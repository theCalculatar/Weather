package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.BuildConfig

import com.example.weather.network.NetworkService
import com.example.weather.network.model.CityResponseApi
import com.example.weather.network.model.ResponseApi
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private val networkService = NetworkService()
    private val apiKey = BuildConfig.apiKey
    private val _data = MutableLiveData<ResponseApi>()
    val data:LiveData<ResponseApi> =_data

    private val _error = MutableLiveData<CityResponseApi>()
    val error:LiveData<CityResponseApi?> =_error

    fun getDataCord(lat:Double,lng:Double) {
        networkService.weatherService()
            .getHourlyData(lat,lng,apiKey).enqueue(object : Callback<ResponseApi> {
            override fun onResponse(
                call: Call<ResponseApi>,
                response: Response<ResponseApi>
            ) {
                Log.d("leeApi", response.body()?.message!!.toString())

                if (response.code() == 200) {
                    _data.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.d("leeApi",t.message!!)
            }

        })
    }

    fun getDataCity(city:String){
        _error.postValue(null)
        networkService.weatherService()
            .getHourlyData(city,apiKey).enqueue(object : Callback<CityResponseApi> {
                override fun onResponse(
                    call: Call<CityResponseApi>,
                    response: Response<CityResponseApi>
                ) {
                    Log.d("leeApi",Gson().toJson(response.body()).toString())
                    if (response.code() == 200) {
                        _error.postValue(response.body()!!)
                        getDataCord(response.body()?.city?.coord!!.lat,response.body()?.city?.coord!!.lat)
                    }else{
                        response.body()?.let {
                            _error.postValue(it)
                        }?: run {
                            _error.postValue(
                                CityResponseApi(
                                    "404", "Invalid City", null
                                )
                            )
                        }

                    }
                }
                override fun onFailure(call: Call<CityResponseApi>, t: Throwable) {
                    Log.d("leeApi",t.message!!)
                }

            })
    }



}