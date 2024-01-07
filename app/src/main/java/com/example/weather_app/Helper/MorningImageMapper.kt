package com.example.weather_app.Helper

import com.example.weather_app.R

object MorningImageMapper {
    private val weatherImage = mapOf(
        0 to R.drawable.sunny,
        1 to R.drawable.sunny,
        2 to R.drawable.cloudy,
        3 to R.drawable.cloud,
        45 to R.drawable.cloudy_2,
        48 to R.drawable.cloudy_2,
        51 to R.drawable.rainy,
        53 to R.drawable.rainy,
        55 to R.drawable.rainy,
        56 to R.drawable.rainy,
        57 to R.drawable.rainy,
        61 to R.drawable.rainy,
        63 to R.drawable.rainy,
        65 to R.drawable.rainy,
        66 to R.drawable.rainy,
        67 to R.drawable.rainy,
        71 to R.drawable.snowy,
        73 to R.drawable.snowy,
        75 to R.drawable.snowy,
        77 to R.drawable.snowy,
        80 to R.drawable.rainy,
        81 to R.drawable.rainy,
        82 to R.drawable.rainy,
        85 to R.drawable.snowy,
        86 to R.drawable.snowy,
        95 to R.drawable.storm,
        96 to R.drawable.storm,
        99 to R.drawable.storm
    )

    fun getMorningImage(weatherCode: Int): Int {
        return weatherImage[weatherCode] ?: -1
    }
}