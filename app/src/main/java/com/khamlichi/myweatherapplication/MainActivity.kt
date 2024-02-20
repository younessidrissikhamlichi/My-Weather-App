package com.khamlichi.myweatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var editCityName: EditText
    lateinit var btnSearch: Button
    lateinit var imageWeather: ImageView
    lateinit var tvTemperature: TextView
    lateinit var tvCityName: TextView

    lateinit var layoutWeather: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCityName = findViewById(R.id.editCity)
        btnSearch = findViewById(R.id.btnSearch)
        imageWeather = findViewById(R.id.imageWeather)
        tvCityName = findViewById(R.id.tvCityName)
        tvTemperature = findViewById(R.id.tvTemperature)
        layoutWeather = findViewById(R.id.layoutWeather)

        btnSearch.setOnClickListener {
            val city = editCityName.text.toString()
            if(city.isEmpty()) {
                Toast.makeText(this, "City name can't be empty!!", Toast.LENGTH_LONG).show()
            } else {
                getWeatherByCity(city)
            }
        }
    }

    fun getWeatherByCity(city: String) {
        // Create retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            //     .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherService::class.java)

        // Call Weather api
        val result = weatherService.getWeatherByCity(city)

        // Appeler le résultat dans une pile
        result.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    //val main = result?.get("main")?.asJsonObject
                    //val temp = main?.get("temp")?.asDouble
                    //val cityName = result?.get("name")?.asString

                    // Récupérer l'image
                    //val weather = result?.get("weather")?.asJsonArray
                    //val icon = weather?.get(0)?.asJsonObject?.get("icon")?.asString



                    //Picasso.get().load("https://openweathermap.org/img/w/$icon.png").into(imageWeather)


                    //tvTemperature.text = "$temp °C"
                    //tvCityName.text = cityName

                    tvTemperature.text = "${result?.main?.temp} °C"
                    tvCityName.text = result?.name
                    Picasso.get().load("https://openweathermap.org/img/w/${result?.weather?.get(0)?.icon}.png").into(imageWeather)

                    layoutWeather.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(applicationContext, "Erreur Serveur", Toast.LENGTH_LONG).show()
            }

        })

    }

}
