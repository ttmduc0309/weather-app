package com.example.weather_app

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.overview.OverviewViewModel
import com.example.weather_app.Adapter.HourlyAdapter
import com.example.weather_app.Helper.MorningImageMapper
import com.example.weather_app.Helper.NightImageMapper
import com.example.weather_app.Helper.WeatherMapper
import com.example.weather_app.Model.HourlyView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    val CITY = "Hanoi"
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val current = Calendar.getInstance().time
        // Format the time using a DateTimeFormatter
        val formatter = SimpleDateFormat("MMMM, dd")
        findViewById<TextView>(R.id.date_holder).text= formatter.format(current)


        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return OverviewViewModel("Hanoi") as T
            }
        }
        val overViewModel: OverviewViewModel by lazy {
            ViewModelProvider(this, factory).get(OverviewViewModel::class.java)
        }

        overViewModel.geocode.observe(this, Observer { newData ->
            // newData contains the updated value of yourLiveData
            // You can use this value as needed
            Log.d("LiveData", "New data received: ${newData?.results?.get(0)?.name}")
            // Example: Update a TextView with the new data
            findViewById<TextView>(R.id.address ).text=
                "${newData?.results?.get(0)?.name}, ${newData?.results?.get(0)?.country_code}"
            overViewModel.getWeather(newData?.results?.get(0)?.latitude,newData?.results?.get(0)?.longitude)
            overViewModel.getHourlyWeather(newData?.results?.get(0)?.latitude,newData?.results?.get(0)?.longitude)
        })

        overViewModel.weather.observe(this, Observer { newData ->
            // newData contains the updated value of yourLiveData
            // You can use this value as needed
            Log.d("LiveData", "New data received: ${newData}")
            val des = WeatherMapper.getWeatherCondition(newData.current.weather_code)
            val maxTemp = newData.daily.temperature_2m_max[0].roundToInt()
            val minTemp = newData.daily.temperature_2m_min[0].roundToInt()
            val weatherDes = "$des \n Max: $maxTemp°C Min: $minTemp°C"
            // Example: Update a TextView with the new data
            val overImg=findViewById<ImageView>(R.id.imageView)
            val hourformatter = SimpleDateFormat("HH",Locale.ENGLISH)
            var curentTime = hourformatter.format(current).toInt()
            if(curentTime in 6..17){
                overImg.setImageResource(MorningImageMapper.getMorningImage(newData.current.weather_code))
            }else {
                overImg.setImageResource(NightImageMapper.getNightImage(newData.current.weather_code))
            }
            findViewById<TextView>(R.id.textView2).text = weatherDes
            findViewById<TextView>(R.id.tempText).text = "${newData.current.temperature_2m.roundToInt()}°C"
            findViewById<TextView>(R.id.humid).text = "${newData.current.relative_humidity_2m}%"
            findViewById<TextView>(R.id.feel_like).text = "${newData.current.apparent_temperature.roundToInt()}°C"
            findViewById<TextView>(R.id.wind_speed).text = "${newData.current.wind_speed_10m.roundToInt()}km/h"

        })

        overViewModel.hourly.observe(this, Observer { newData ->
            // newData contains the updated value of yourLiveData
            // You can use this value as needed
            Log.d("LiveData", "New data received: ${newData}")

            val dateformatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val hourformatter = SimpleDateFormat("HH",Locale.ENGLISH)
            val timeQuery = dateformatter.format(current)+"T"+hourformatter.format(current)+":00"

            val currentIndex=newData.hourly.time.indexOf(timeQuery)
            Log.d("timely: ",timeQuery)
            val data = ArrayList<HourlyView>()
            var curentTime = hourformatter.format(current).toInt()
            for (i in currentIndex+1..currentIndex+12)
            {
                var imageId =-1
                if(curentTime<23){
                    curentTime++
                }else curentTime=0

                if(curentTime in 6..17){
                    imageId=MorningImageMapper.getMorningImage(newData.hourly.weather_code[i])
                }else{
                    imageId=NightImageMapper.getNightImage(newData.hourly.weather_code[i])
                }
                data.add(HourlyView(
                    String.format("%02d:00", curentTime),
                    newData.hourly.temperature_2m[i].roundToInt(),
                    imageId))
            }
            val recyclerview = findViewById<RecyclerView>(R.id.recycleview)
            recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            val adapter = HourlyAdapter(data)

            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter
        })
    }


}