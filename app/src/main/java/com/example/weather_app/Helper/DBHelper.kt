package com.release.gfg1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.MutableLiveData
import com.example.weather_app.DBmodel.CurrentWeather
import com.example.weather_app.DBmodel.HourlyWeather
import com.example.weather_app.Model.Current

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query1 = ("CREATE TABLE " + TABLE_NAME1 + " ("
                + ID_COL1 + " INTEGER PRIMARY KEY, " +
                CODE_COL + " TEXT," +
                NAME_COL + " TEXT," +
                LAT_COL + " REAL," +
                LONG_COL + " REAL" +")")

        val query2 = ("CREATE TABLE " + TABLE_NAME2 + " ("
                + ID_COL2 + " INTEGER PRIMARY KEY, " +
                TEMP_COL + " INTEGER," +
                HUMID_COL + " INTEGER," +
                FEELLIKE_COL + " INTEGER," +
                WIND_COL + " INTEGER," +
                WEATHER_CODE_COL1 + " INTEGER," +
                TIME_COL + " TEXT," +
                MAX_COL + " INTEGER," +
                MIN_COL + " INTEGER" +")")

        val query3 = ("CREATE TABLE " + TABLE_NAME3 + " ("
                + ID_COL3 + " INTEGER PRIMARY KEY, " +
                HOUR_COL + " INTEGER," +
                TEMP_COL2 + " INTEGER," +
                WEATHER_CODE_COL2 + " INTEGER" +")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query1)
        db.execSQL(query2)
        db.execSQL(query3)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3)
        onCreate(db)
    }

    fun resetData() {
        val db = this.writableDatabase
        db.execSQL("delete from "+ TABLE_NAME1);
        db.execSQL("delete from "+ TABLE_NAME2);
        db.execSQL("delete from "+ TABLE_NAME3);
    }

    // This method is for adding data in our database
    fun addGeoData(countrycode:String,name:String,latitude:Double,longitude:Double){
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(CODE_COL, countrycode)
            put(NAME_COL, name)
            put(LAT_COL, latitude)
            put(LONG_COL, longitude)
        }

        db.insert(TABLE_NAME1, null, values)
        db.close()
    }

    fun addCurrentData(temp:Int,humid:Int,feel_like:Int, wind:Int,
                       weather_code:Int,time:String,max_temp:Int,
                       min_temp:Int){

        val values = ContentValues()
        values.put(TEMP_COL, temp)
        values.put(HUMID_COL, humid)
        values.put(FEELLIKE_COL, feel_like)
        values.put(WIND_COL, wind)
        values.put(WEATHER_CODE_COL1, weather_code)
        values.put(MAX_COL,max_temp)
        values.put(MIN_COL, min_temp)
        values.put(TIME_COL, time)
        val db = this.writableDatabase

        db.insert(TABLE_NAME2, null, values)
        db.close()
    }

    fun addHourlyData(temp:Int,hour:Int,weather_code: Int){

        val values = ContentValues()
        values.put(TEMP_COL2, temp)
        values.put(HOUR_COL, hour)
        values.put(WEATHER_CODE_COL2,weather_code)
        val db = this.writableDatabase

        db.insert(TABLE_NAME3, null, values)
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getGeoData(): MutableLiveData<String> {
        val db = this.readableDatabase
        val cursor= db.rawQuery("SELECT * FROM $TABLE_NAME1", null)
        val address = MutableLiveData<String>()

        while(cursor.moveToNext()){
            val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL))
            val code = cursor.getString(cursor.getColumnIndexOrThrow(CODE_COL))
            address.value=name+", "+code
        }
        return address
    }
    fun getCurrentData(): MutableLiveData<CurrentWeather> {
        val db = this.readableDatabase
        val cursor= db.rawQuery("SELECT * FROM " + TABLE_NAME2, null)
        val data = MutableLiveData<CurrentWeather>()
        while(cursor.moveToNext()){
            val temp = cursor.getInt(cursor.getColumnIndexOrThrow(TEMP_COL))
            val feellike = cursor.getInt(cursor.getColumnIndexOrThrow(FEELLIKE_COL))
            val humid = cursor.getInt(cursor.getColumnIndexOrThrow(HUMID_COL))
            val wind = cursor.getInt(cursor.getColumnIndexOrThrow(WIND_COL))
            val code = cursor.getInt(cursor.getColumnIndexOrThrow(WEATHER_CODE_COL1))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(TIME_COL))
            val maxtemp = cursor.getInt(cursor.getColumnIndexOrThrow(MAX_COL))
            val mintemp = cursor.getInt(cursor.getColumnIndexOrThrow(MIN_COL))

            data.value= CurrentWeather(temp,wind,humid,feellike,code,time,maxtemp, mintemp)
        }
        return data
    }
    fun getHourlyData(): MutableList<HourlyWeather> {
        val db = this.readableDatabase
        val cursor= db.rawQuery("SELECT * FROM " + TABLE_NAME3, null)
        val data = mutableListOf<HourlyWeather>()

        while(cursor.moveToNext()){
            val temp =cursor.getInt(cursor.getColumnIndexOrThrow(TEMP_COL2))
            val hour =cursor.getInt(cursor.getColumnIndexOrThrow(HOUR_COL))
            val code =cursor.getInt(cursor.getColumnIndexOrThrow(WEATHER_CODE_COL2))
            data.add(HourlyWeather(temp,hour,code))
        }
        return data
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Weather-App"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME1 = "geocode"
        val ID_COL1 = "id"
        val CODE_COL= "country_code"
        val NAME_COL= "name"
        val LAT_COL = "latitude"
        val LONG_COL = "longitude"

        // below is the variable for table name
        val TABLE_NAME2 = "current_weather"
        val ID_COL2 = "id"
        val TEMP_COL= "temp"
        val WIND_COL= "wind"
        val HUMID_COL = "humid"
        val FEELLIKE_COL ="feel_like"
        val MIN_COL = "min_temp"
        val MAX_COL ="max_temp"
        val TIME_COL ="time"
        val WEATHER_CODE_COL1 ="weather_code"

        // below is the variable for table name
        val TABLE_NAME3 = "hourly_weather"
        val ID_COL3 = "id"
        val HOUR_COL = "hour"
        val TEMP_COL2 ="temp"
        val WEATHER_CODE_COL2 ="weather_code"

    }
}
