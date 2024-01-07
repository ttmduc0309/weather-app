package com.example.weather_app.Model

data class Current(
    val apparent_temperature: Double,
    val interval: Int,
    val relative_humidity_2m: Int,
    val temperature_2m: Double,
    val time: String,
    val wind_speed_10m: Double,
    val weather_code:Int,
    val is_day:Int
)