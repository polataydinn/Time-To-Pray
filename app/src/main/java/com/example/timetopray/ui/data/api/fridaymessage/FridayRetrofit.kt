package com.example.timetopray.ui.data.api.fridaymessage

import com.example.timetopray.ui.constants.Constants
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FridayRetrofit {

    val retrofit: retrofit2.Retrofit by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(Constants.FRIDAY_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.MILLISECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: FridayMessagesApi by lazy {
        retrofit.create(FridayMessagesApi::class.java)
    }
}
