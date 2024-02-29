package com.example.weather.network.api

import com.example.weather.network.model.CityResponseApi
import com.example.weather.network.model.ResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TempApi {
    @GET("3.0/onecall?units=metric")
    fun getHourlyData(@Query("lat") lat: Double, @Query("lon") lon: Double,
                      @Query("appid") api:String): Call<ResponseApi>
    @GET("2.5/forecast?units=metric")
    fun getHourlyData(@Query("q") city:String,@Query("appid") api:String): Call<CityResponseApi>
}