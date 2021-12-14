package com.example.timetopray.ui.data.models.ayats


import com.google.gson.annotations.SerializedName

data class Ayats(
    @SerializedName("Ayats")
    val ayats: List<Ayat?>?,
    @SerializedName("Info")
    val info: Info?
)



