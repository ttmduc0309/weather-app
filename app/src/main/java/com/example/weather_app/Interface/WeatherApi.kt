package com.example.weather_app.Interface

import com.example.weather_app.Model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude:Double?,
        @Query("longitude") longitude: Double?,
        @Query("current") current:List<String>?,
        @Query("daily") daily:List<String>?,
    ) : Response<Weather>
}