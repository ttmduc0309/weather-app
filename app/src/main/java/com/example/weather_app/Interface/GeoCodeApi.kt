package com.example.weather_app.Interface

import com.example.weather_app.Model.Geocode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeApi {
    @GET("search")
    suspend fun getGeocode(
        @Query("name") name:String?,
        @Query("count") count:Int?,
        @Query("language") language:String?,
        @Query("format") format:String?,
    ) : Response<Geocode>
}