package com.example.weather_app.Helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherHelper{
    val baseUrl = "https://api.open-meteo.com/v1/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}