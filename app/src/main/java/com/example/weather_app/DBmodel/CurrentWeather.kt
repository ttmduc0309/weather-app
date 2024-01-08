package com.example.weather_app.DBmodel

data class CurrentWeather(
    val temp:Int,
    val wind:Int,
    val humid:Int,
    val feellike:Int,
    val weathercode:Int,
    val time:String,
    val maxtemp:Int,
    val mintemp:Int
)