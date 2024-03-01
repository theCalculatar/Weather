package com.example.weather.network.model

import com.example.weather.model.*

class ResponseApi(val timezone:String,
                  val lat: Double,
                  val lon: Double,
                  val current: Current,
                  val message:Int,
                  val cnt:Int,
                  val hourly:List<Hourly>,
                  val daily: List<Daily>
                  )