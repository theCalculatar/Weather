package com.example.weather.network.model

import com.example.weather.model.City

class CityResponseApi(
    val cod: String,
    val message: String,
    val city: City?
                      )