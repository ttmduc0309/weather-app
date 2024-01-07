package com.example.weather_app.Model

data class MeteoHourly(
    val hourly: Hourly,
    val latitude: Int,
    val longitude: Double,
)