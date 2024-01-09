package com.example.weather_app.Helper

object WeatherMapper {
        private val weatherMap = mapOf(
            0 to "Clear Sky",
            1 to "Mainly Clear",
            2 to "Partly Cloudy",
            3 to "Overcast",
            45 to	"Fog",
            48 to "Rime Fog",
            51 to "Light Drizzle",
            53 to "Moderate Drizzle",
            55 to "Dense Drizzle",
            56 to "Light Freezing Drizzle",
            57 to "Dense Freezing Drizzle",
            61 to "Slight Rain",
            63 to "Moderate Rain",
            65 to "Heavy Rain",
            66 to "Light Freezing Rain",
            67 to "Heavy Freezing Rain",
            71 to "Light Snow Fall",
            73 to "Moderate Snow Fall",
            75 to "Heavy Snow Fall",
            77 to "Snow grains",
            80 to "Slight Rain Showers",
            81 to "Moderate Rain Showers",
            82 to "Violent Rain Showers",
            85 to "Slight Snow Showers",
            86 to "Heavy Snow Showers",
            95 to "Thunderstorm",
            96 to "Thunderstorm with slight hail",
            99 to "Thunderstorm with heavy hail"
        )

        fun getWeatherCondition(weatherCode: Int): String {
            return weatherMap[weatherCode] ?: "Unknown Weather"
        }
}