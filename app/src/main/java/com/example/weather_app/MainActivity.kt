package com.example.weather_app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.Helper.GeocodeHelper
import com.example.weather_app.Helper.WeatherHelper
import com.example.weather_app.Interface.GeoCodeApi
import com.example.weather_app.Interface.WeatherApi
import com.example.weather_app.ViewModel.WeatherView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    val CITY = "Hanoi"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var result:Response<Geocode>
        val geoApi = GeocodeHelper.getInstance().create(GeoCodeApi::class.java)
        val weatherApi = WeatherHelper.getInstance().create(WeatherApi::class.java)
        // launching a new coroutine

        CoroutineScope(IO).launch {

            val resultA = async{ geoApi.getGeocode(CITY, 1, "en", "json") }.await()

            val resultB = async{ weatherApi.getWeather(resultA.body()?.results?.get(0)?.latitude,
                resultA.body()?.results?.get(0)?.longitude,
                listOf("temperature_2m","relative_humidity_2m","apparent_temperature","wind_speed_10m"),
                listOf("temperature_2m_max","temperature_2m_min")
            ) }.await()
            if (resultB != null&& resultA != null){
                Log.d("ayush: ",resultB.body().toString())
                withContext(Dispatchers.Main) {
                    val weatherView= WeatherView(
                        resultB.body()?.current?.temperature_2m?.roundToInt().toString()+"ºC",
                        resultA.body()?.results?.get(0)?.name+", "+resultA?.body()?.results?.get(0)?.country_code,
                        resultB?.body()?.current?.relative_humidity_2m.toString()+"%",
                        resultB?.body()?.current?.apparent_temperature?.roundToInt().toString()+"ºC",
                        resultB?.body()?.current?.wind_speed_10m?.roundToInt().toString()+"km/h")
                    findViewById<TextView>(R.id.tempText).text= weatherView.temp
                    findViewById<TextView>(R.id.address).text= weatherView.location
                    findViewById<TextView>(R.id.humid).text= weatherView.humidity
                    findViewById<TextView>(R.id.feel_like).text= weatherView.feelLike
                    findViewById<TextView>(R.id.wind_speed).text= weatherView.windSpeed
                    val time = Calendar.getInstance().time
                    val formatter = SimpleDateFormat(/* pattern = */ "MMMM, dd")
                    findViewById<TextView>(R.id.date_holder).text= formatter.format(time)
                }
        }
        }
//        val recyclerview = findViewById<RecyclerView>(R.id.recycleview)
//
//        // this creates a vertical layout Manager
//        recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//
//        // ArrayList of class ItemsViewModel
//        val data =ArrayList<Hourly>()
//        data.add(Hourly("10:00",10))
//        data.add(Hourly("11:00",11))
//        data.add(Hourly("11:00",11))
//        data.add(Hourly("11:00",11))
//        data.add(Hourly("11:00",11))
//        data.add(Hourly("11:00",11))
//
//        // This will pass the ArrayList to our Adapter
//        val adapter = HourlyAdapter(data)
//
//        // Setting the Adapter with the recyclerview
//        recyclerview.adapter = adapter
    }


}