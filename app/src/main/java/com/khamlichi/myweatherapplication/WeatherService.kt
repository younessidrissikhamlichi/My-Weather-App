package com.khamlichi.myweatherapplication

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    //@GET("?q=Fez&appid=9f087e8e9e8122b915066d267667cd01?units=metric")
    //fun getWeatherByCity(): Call<String>
    // fun getWeatherByCity(): Call<JsonObject>
    companion object {
        const val API_KEY = "9f087e8e9e8122b915066d267667cd01"
    }

    @GET("?units=metric&appid=$API_KEY")
    fun getWeatherByCity(@Query("q") city: String): Call<WeatherResult>
}