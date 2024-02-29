package com.example.weather.network

import com.example.weather.model.Weather
import com.example.weather.network.api.TempApi
import com.example.weather.network.model.ResponseApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {

    private val url = "https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=-23.903081625764052&lon=29.746948324457588&appid={48bab716d427ec28ce793beff45be7f6/"

    fun weatherService(): TempApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_API_URL) // as we are sending data in json format so
            // we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create()) // at last we are building our retrofit builder.
            .build()
        return retrofit.create(TempApi::class.java)
    }
}