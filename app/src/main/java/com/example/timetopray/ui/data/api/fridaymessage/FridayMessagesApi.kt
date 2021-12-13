package com.example.timetopray.ui.data.api.fridaymessage

import com.example.timetopray.ui.data.models.fridaymessages.FridayMessages
import retrofit2.Call
import retrofit2.http.GET

interface FridayMessagesApi {

    @GET("polataydinn/2aeb78db58c4275e2d4fde6d5653b81e/raw/43b12d73bfb57a101ee5aa793d9d3310fc065f66/listOfFridayMessages.json")
    fun getAllMessages(): Call<FridayMessages>
}