package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class Story (

    @SerializedName("id") val id : Int,
    @SerializedName("question_story") val question_story : QuestionStory,
)

data class QuestionStory(
    @SerializedName("story_text") val story_text : String
)