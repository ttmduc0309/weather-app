package com.example.weather_app.Model

data class Result(
    val country: String,
    val country_code: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
)