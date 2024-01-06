package com.example.weather_app.Model

data class Result(
    val admin1: String,
    val admin1_id: Int,
    val country: String,
    val country_code: String,
    val country_id: Int,
    val elevation: Int,
    val feature_code: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val population: Int,
    val timezone: String
)