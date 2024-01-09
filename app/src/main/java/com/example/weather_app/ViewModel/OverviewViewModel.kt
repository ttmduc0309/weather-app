package com.example.android.marsphotos.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.Helper.GeocodeHelper
import com.example.weather_app.Helper.WeatherHelper
import com.example.weather_app.Interface.GeoCodeApi
import com.example.weather_app.Interface.MeteoHourlyApi
import com.example.weather_app.Interface.WeatherApi
import com.example.weather_app.Model.Geocode
import com.example.weather_app.Model.MeteoHourly
import com.example.weather_app.Model.Weather
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel(cityData:String): ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _geocode = MutableLiveData<Geocode>()
    // The external immutable LiveData for the request status
    val geocode: MutableLiveData<Geocode> = _geocode

    // The internal MutableLiveData that stores the status of the most recent request
    private val _weather = MutableLiveData<Weather>()
    // The external immutable LiveData for the request status
    val weather: MutableLiveData<Weather> = _weather

    // The internal MutableLiveData that stores the status of the most recent request
    private val _hourly = MutableLiveData<MeteoHourly>()
    // The external immutable LiveData for the request status
    val hourly: MutableLiveData<MeteoHourly> = _hourly

    private var _city: String
    private val city: String
        get() = _city

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        _city=cityData
        getGeocodeData()
    }

    fun newCity(cityData: String)
    {
        _city=cityData
        getGeocodeData()
        Log.d("ViewModelInit", "ViewModel showed city geocode : ${cityData}")
    }
    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getGeocodeData() {
        viewModelScope.launch {
            try {
                val geocodeApi = GeocodeHelper.getInstance().create(GeoCodeApi::class.java)
                val listResult = geocodeApi.getGeocode(city,1,"en","json")

                _geocode.value = listResult.body()
                Log.d("ViewModelInit", "ViewModel showed geocode like this: ${listResult}")

            } catch (e: Exception) {
                Log.d("error: ", "Failure: ${e.message}")
            }
        }
    }

    fun getWeather(lat: Double?, long:Double?){
        viewModelScope.launch {
            try {
                val weatherApi = WeatherHelper.getInstance().create(WeatherApi::class.java)
                val listResult = weatherApi.getWeather(
                    lat,long,
                    listOf("temperature_2m","relative_humidity_2m","apparent_temperature","wind_speed_10m","weather_code","is_day"),
                    listOf("temperature_2m_max","temperature_2m_min")
                )
                _weather.value = listResult.body()
            } catch (e: Exception) {
                Log.d("error: ", "Failure: ${e.message}")
            }
        }
    }

    fun getHourlyWeather(lat: Double?, long:Double?){
        viewModelScope.launch {
            try {
                val hourlyApi = WeatherHelper.getInstance().create(MeteoHourlyApi::class.java)
                val listResult = hourlyApi.getMeteoHourly(lat,long, listOf("temperature_2m","weather_code","is_day"))
                _hourly.value = listResult.body()
            } catch (e: Exception) {
                Log.d("error: ", "Failure: ${e.message}")
            }
        }
    }
}
