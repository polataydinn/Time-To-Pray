package com.example.timetopray.ui.data.api.fridaymessage

import com.example.timetopray.ui.data.models.fridaymessages.FridayMessages
import retrofit2.Call
import retrofit2.http.GET

interface FridayMessagesApi {
    @GET("polataydinn/2aeb78db58c4275e2d4fde6d5653b81e/raw/857152574143e5249094f1d02fc12efdf959159c/listOfFridayMessages.json")
    fun getAllMessages(): Call<FridayMessages>
}