package com.example.weather.model

class Daily(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val temp: Temperature,
    val pressure: Double,
    val humidityL: Int,
    val uvi: Double,
    val visibility: Int,
    val wind_speeds: Double,
    val weather: List<Weather>
)