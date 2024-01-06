package com.example.weather_app.Model

data class Geocode(
    val generationtime_ms: Double,
    val results: List<Result>
)