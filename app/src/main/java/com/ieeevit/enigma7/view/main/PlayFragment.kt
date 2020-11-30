package com.ieeevit.enigma7.view.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.model.CloseAnswer
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.viewModel.PlayViewModel
import com.ieeevit.enigma7.work.RefreshXpWorker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.enigma_title.view.*
import kotlinx.android.synthetic.main.hint_dialog_layout.view.*
import kotlinx.android.synthetic.main.powerup_confirm_dialog_skip.view.powerup_cancel
import kotlinx.android.synthetic.main.powerup_confirm_dialog_skip.view.powerup_confirm
import kotlinx.android.synthetic.main.powerups_layout.view.*
import kotlinx.android.synthetic.main.progress_layout.view.*
import kotlinx.android.synthetic.main.questions_layout.view.*
import kotlinx.android.synthetic.main.view_hint_dialog.view.*
import kotlinx.android.synthetic.main.view_hint_dialog.view.errorview
import kotlinx.android.synthetic.main.xp_alert_dialog.view.*
import java.util.regex.Pattern

class PlayFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    private lateinit var authCode: String
    private lateinit var builder: AlertDialog.Builder
    private lateinit var customInflater: LayoutInflater
    private lateinit var hint: String
    private lateinit var answer: String
    private lateinit var pattern: Pattern
    private val viewModel: PlayViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, PlayViewModel.Factory(activity.application))
            .get(PlayViewModel::class.java)
    }
    private lateinit var overlayFrame: ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pattern = Pattern.compile("[0-9a-zA-Z ]*")
        val root: View = inflater.inflate(R.layout.fragment_play, container, false)
        overlayFrame = root.findViewById(R.id.overlayFrame)
        overlayFrame.visibility = VISIBLE
        sharedPreference = PrefManager(this.requireActivity())
        if (sharedPreference.getHintString() != null) {
            root.get_hint_btn.visibility = GONE
            root.view_hint_btn.visibility = VISIBLE
        }
        init()
        viewModel.status.observe(viewLifecycleOwner, {
            showAlertDialog(R.layout.xp_alert_dialog)
            overlayFrame.visibility = GONE
        })

        viewModel.hint.observe(viewLifecycleOwner, {
            if (it == "") {
                overlayFrame.visibility = GONE
                Snackbar.make(root.rootView,"Hint retrieval failed",Snackbar.LENGTH_SHORT).show()
            } else if (it != null) {
                overlayFrame.visibility = GONE
                hint = it
                if (sharedPreference.getHintString() == null) {
                    sharedPreference.setHint(hint)
                    if (sharedPreference.isShowHintDialog()) {
                        showAlertDialog(R.layout.view_hint_dialog)
                    }

                }
                root.get_hint_btn.visibility = GONE
                root.view_hint_btn.visibility = VISIBLE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            if (it == 1) {
                overlayFrame.visibility = GONE
            }
        })
        viewModel.skipStatus.observe(viewLifecycleOwner, {
            if (it == 1) {
                root.get_hint_btn.visibility = VISIBLE
                root.view_hint_btn.visibility = GONE
                overlayFrame.visibility = GONE
                viewModel.getStory(authCode)
                viewModel.refreshQuestionsFromRepository("Token $authCode")
                viewModel.refreshUserDetailsFromRepository("Token $authCode")
                sharedPreference.setHint(null)
            }
        })
        viewModel.closeAnswerStatus.observe(viewLifecycleOwner, {
            if (it == 1) {
                root.get_hint_btn.visibility = VISIBLE
                root.view_hint_btn.visibility = GONE
                overlayFrame.visibility = GONE
                sharedPreference.setHint(null)
                viewModel.getStory(authCode)
            }
        })
        viewModel.userDetails.observe(viewLifecycleOwner, {
            if (it != null) {
                sharedPreference.setUsername(it.username)
                val xp: Int = it.xp!!
                Log.i("XP", xp.toString())
                sharedPreference.setXp(xp)
                val xpFromPref = sharedPreference.getXp()
                root.progress_bar.progress = xpFromPref
                val percentage = xpFromPref.toString() + "xp"
                root.progress_percent.text = percentage
                if (xp == 100) {
                    WorkManager.getInstance().cancelUniqueWork(RefreshXpWorker.WORK_NAME)
                }
            }
        })
        viewModel.powerUpStatus.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.hintPowerup!! || it.hintUsed!! && it.hintUsed != null) {
                    viewModel.getHint("Token $authCode")
                } else {
                    root.get_hint_btn.visibility = VISIBLE
                    root.view_hint_btn.visibility = GONE
                    sharedPreference.setHint(null)
                }

            }
        })
        viewModel.story.observe(viewLifecycleOwner, {
            if (it?.question_story != null) {
                var text=""
                var word=""
                for (i:Char in it.question_story.story_text){
                    if(i!= ' ') word+=i
                    else{
                        when {
                            word.contains("<br>") -> {
                                text+="\n"
                                word=""
                            }
                            word.contains("<username>") -> {
                                text+="${sharedPreference.getUsername()}: "
                                word=""
                            }
                            word.contains("<4747>") -> {
                                text+="4747: "
                                word=""
                            }
                            else -> {
                                text+="$word "
                                word=""
                            }
                        }
                    }
                }
                val words=it.question_story.story_text.split(' ')
                text+=words[words.size-1]
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                    .replace(R.id.container, StorySnippetFragment(text))
                    .commit()
            }
        })
        viewModel.answerResponse.observe(viewLifecycleOwner, {
            overlayFrame.visibility = GONE
            if (it != null) {
                when {
                    it.closeAnswer == true -> {
                        showAlertDialog(R.layout.close_response_dialog)
                    }
                    it.answer == false -> {
                        showAlertDialog(R.layout.wrong_response_dialog)
                    }
                    it.answer == true -> {
                        showAlertDialog(R.layout.correct_response_dialog)
                        viewModel.getStory(authCode)
                        root.get_hint_btn.visibility = VISIBLE
                        root.view_hint_btn.visibility = GONE
                        sharedPreference.setHint(null)
                        root.answerBox.setText("")
                        overlayFrame.visibility = VISIBLE
                        viewModel.refreshQuestionsFromRepository("Token $authCode")
                    }
                }
            }
        })
        viewModel.questionResponse.observe(viewLifecycleOwner, {

            if (it != null) {
                overlayFrame.visibility = GONE
                root.question.text = it.text
                val id = "Q${it.id}."
                root.question_id.text = id
                Picasso.get().load(it.img_url).into(root.question_image)
            }

        })

        viewModel.refreshQuestionsFromRepository("Token $authCode")
        viewModel.refreshUserDetailsFromRepository("Token $authCode")
        viewModel.getPowerUpStatus("Token $authCode")

        root.submit_btn.setOnClickListener {
            overlayFrame.visibility = VISIBLE
            val answer = root.answerBox.text.toString().trimEnd()
            val matcher = pattern.matcher(answer)
            if(!matcher.matches()){
              showAlertDialog(R.layout.special_character_dialog)
                overlayFrame.visibility = GONE
            }else{
                viewModel.checkAnswer("Token $authCode", answer)
            }
        }

        root.get_hint_btn.setOnClickListener {
            showAlertDialog(R.layout.hint_dialog_layout)
        }
        root.view_hint_btn.setOnClickListener {
            showAlertDialog(R.layout.view_hint_dialog)
        }
        root.instructions.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                .replace(R.id.container, InstructionsFragment())
                .commit()
        }
        root.closeAnswerPowerup.setOnClickListener {
            answer = root.answerBox.text.toString()
            if (answer.isEmpty()) {
                showAlertDialog(R.layout.answer_box_null_layout)
            } else {
                showAlertDialog(R.layout.powerup_confirm_dialog_close)
            }

        }
        root.skipPowerUp.setOnClickListener { showAlertDialog(R.layout.powerup_confirm_dialog_skip) }
        root.hintPowerUp.setOnClickListener { showAlertDialog(R.layout.powerup_confirm_dialog_hint) }

        return root
    }

    private fun init() {
        authCode = sharedPreference.getAuthCode()!!
        builder = AlertDialog.Builder(activity)
        customInflater = requireActivity().layoutInflater
    }

    private fun showAlertDialog(layoutId: Int) {
        val customLayout: View = customInflater.inflate(layoutId, null)
        builder.setView(customLayout)
        val alert = builder.create()
        when (customLayout.id) {
            R.id.hintDialog -> {
                customLayout.cancel.setOnClickListener {
                    alert.dismiss()
                }
                customLayout.accept.setOnClickListener {
                    sharedPreference.setIsShowHintDialog(true)
                    overlayFrame.visibility = VISIBLE
                    viewModel.getHint("Token $authCode")
                    alert.dismiss()
                }
            }
            R.id.viewHintDialog -> {
                customLayout.errorview.text = sharedPreference.getHintString()
                customLayout.close.setOnClickListener {
                    alert.dismiss()
                }
            }
            R.id.confirmPowerupDialogSkip -> {
                customLayout.powerup_confirm.setOnClickListener {
                    viewModel.usePowerUpSkip("Token $authCode")
                    overlayFrame.visibility = VISIBLE
                    alert.dismiss()

                }
                customLayout.powerup_cancel.setOnClickListener { alert.dismiss() }
            }
            R.id.confirmPowerupDialogClose -> {
                customLayout.powerup_confirm.setOnClickListener {
                    viewModel.usePowerUpCloseAnswer("Token $authCode", CloseAnswer(answer))
                    overlayFrame.visibility = VISIBLE
                    alert.dismiss()
                }
                customLayout.powerup_cancel.setOnClickListener { alert.dismiss() }
            }
            R.id.confirmPowerupDialogHint -> {
                customLayout.powerup_confirm.setOnClickListener {
                    sharedPreference.setIsShowHintDialog(true)
                    viewModel.usePowerUpHint("Token $authCode")
                    overlayFrame.visibility = VISIBLE
                    alert.dismiss()
                }
                customLayout.powerup_cancel.setOnClickListener { alert.dismiss() }
            }
            R.id.xpAlertDialog -> {
                customLayout.powerUpStatus.text = viewModel.status.value.toString()
                customLayout.setOnClickListener { alert.dismiss() }
            }
        }
        alert.show()
    }

}