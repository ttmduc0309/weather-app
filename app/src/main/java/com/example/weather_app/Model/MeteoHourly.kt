package com.example.weather_app.Model

data class MeteoHourly(
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
)