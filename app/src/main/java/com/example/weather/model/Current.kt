package com.example.weather.model

class Current(
    val dt: Long,
    val sunrise:Long,
    val sunset:Long,
    val temp: Double,
    val feels_like: Double,
    val pressure: Double,
    val humidity: Int,
    val uvi: Double,
    val visibility: Int,
    val wind_speed: Double,
    val weather: List<Weather>
              )