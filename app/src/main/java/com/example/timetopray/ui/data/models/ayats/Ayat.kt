package com.example.timetopray.ui.data.models.ayats


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "ayats")
data class Ayat(
    @PrimaryKey
    @SerializedName("source")
    val source: String,
    @SerializedName("text")
    val text: String?
)