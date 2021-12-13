package com.example.timetopray.ui.data.models.fridaymessages


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class FridayMessages(
    @SerializedName("FridayMessage")
    val fridayMessage: List<FridayMessageItem?>?
)
