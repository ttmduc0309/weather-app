package com.example.weather_app.Model

data class Weather(
    val current: Current,
    val daily: Daily,
    val latitude: Double,
    val longitude: Double,
)