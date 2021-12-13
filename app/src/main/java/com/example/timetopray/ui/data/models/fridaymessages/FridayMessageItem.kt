package com.example.timetopray.ui.data.models.fridaymessages

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "friday_messages")
data class FridayMessageItem(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,
    @SerializedName("image")
    val image: String?
)
