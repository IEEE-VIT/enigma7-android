package com.ieeevit.enigma7.view.timer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.main.MainActivity
import kotlinx.android.synthetic.main.fragment_countdown.*
import java.util.*

class CountdownFragment : Fragment() {
    private lateinit var startButton: TextView
    private lateinit var sharedPreference: PrefManager
    private lateinit var currentCalendar:Calendar
    private lateinit var eventCalendar:Calendar
    private var currentTime:Long = 0
    private var eventStartTime:Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_countdown, container, false)
        startButton = root.findViewById(R.id.startButton)
        init()
        currentTime = currentCalendar.timeInMillis
        eventStartTime = eventCalendar.timeInMillis
        val timeLeft = eventStartTime - currentTime
        startTimer(timeLeft)
        startButton.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            sharedPreference.setHuntStarted(true)
        }
        return root
    }

    private fun init() {
        sharedPreference = PrefManager(this.requireActivity())
        currentCalendar = Calendar.getInstance(TimeZone.getDefault())
        eventCalendar = Calendar.getInstance(TimeZone.getDefault())
        // TODO: 02-11-2020 Set Event Start Date Here Format: year,month(jan->0),date,hour,minute,second
        eventCalendar.set(2020, 10, 2, 18, 6, 10) // Format: year,month(jan->0),date,hour,minute,second
    }

    private fun startTimer(timeDifference: Long) {
        val countdownTimer = object : CountDownTimer(timeDifference, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerUI(millisUntilFinished)
            }
            override fun onFinish() {
                startButton.visibility = VISIBLE
            }
        }
        countdownTimer.start()
    }

    private fun updateTimerUI(millisUntilFinished: Long) {
        days.text = (millisUntilFinished / (24 * 60 * 60 * 1000)).toString()
        hours.text = (millisUntilFinished / (60 * 60 * 1000) % 24).toString()
        minutes.text = (millisUntilFinished / (60 * 1000) % 60).toString()
        seconds.text = (millisUntilFinished / (1000) % 60).toString()
    }


}