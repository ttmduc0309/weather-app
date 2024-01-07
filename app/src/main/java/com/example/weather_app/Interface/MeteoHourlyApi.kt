package com.example.weather_app.Interface

import com.example.weather_app.Model.MeteoHourly
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoHourlyApi {
    @GET("forecast")
    suspend fun getMeteoHourly(
        @Query("latitude") latitude:Double?,
        @Query("longitude") longtitude:Double?,
        @Query("hourly") hourly:List<String>?,
    ) : Response<MeteoHourly>
}