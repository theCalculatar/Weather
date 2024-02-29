package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.BuildConfig

import com.example.weather.network.NetworkService
import com.example.weather.network.model.ResponseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class HomeViewModel:ViewModel() {

    private val networkService = NetworkService()
    private val apiKey = BuildConfig.apiKey

    fun data(lat:Double,lng:Double,): MutableLiveData<ResponseApi> {
        val data = MutableLiveData<ResponseApi>()
        networkService.weatherService()
            .getHourlyData(lat,lng,apiKey).enqueue(object : Callback<ResponseApi> {
            override fun onResponse(
                call: Call<ResponseApi>,
                response: Response<ResponseApi>
            ) {
                if (response.code() == 200) {
                    data.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.d("leeApi",t.message!!)

            }

        })

        return data
    }


}