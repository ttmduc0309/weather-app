package com.example.weather_app.Model

data class CurrentUnits(
    val apparent_temperature: String,
    val interval: String,
    val relative_humidity_2m: String,
    val temperature_2m: String,
    val time: String,
    val wind_speed_10m: String
)