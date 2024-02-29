package com.example.weather.model

class Hourly(
            val dt: Long,
            val temp: Double,
            val feels_like: Double,
            val temp_min: Double,
            val temp_max: Double,
            val pressure: Double,
            val humidityL: Int,
            val uvi: Double,
            val visibility: Int,
            val wind_speeds: Double,
            val weather: List<Weather>
               )