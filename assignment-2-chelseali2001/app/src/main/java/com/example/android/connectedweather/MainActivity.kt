package com.example.android.connectedweather

import com.example.android.connectedweather.data.ForecastPeriod

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android.connectedweather.data.LongDescription
import com.example.android.connectedweather.data.Temperature
import com.squareup.moshi.Moshi
import com.squareup.moshi.JsonAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {
    private val apiBaseUrl = "https://api.openweathermap.org/data/2.5/forecast?q=Corvallis,OR,US&units=imperial&appid=6ea62b7d1ad89ea0dd964e49ffb0b7b7"

    private val weathAdapter = ForecastAdapter(mutableListOf(ForecastPeriod(date_time = "2022-02-07 06:00:00", temp = Temperature(tempLow = 1.0, tempHigh = 2.0), precip = 3, description = mutableListOf(LongDescription(mainDescript = "Clear", fullDescript = "clear sky")))), ::onWeatherClick)

    private lateinit var requestQueue: RequestQueue

    private lateinit var weatherResultsRV: RecyclerView
    private lateinit var fetchError: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        fetchError = findViewById(R.id.tv_fetch_error)
        loadingIndicator = findViewById(R.id.loading_indicator)

        weatherResultsRV = findViewById(R.id.rv_forecast_list)
        weatherResultsRV.layoutManager = LinearLayoutManager(this)
        weatherResultsRV.setHasFixedSize(true)

        weatherResultsRV.adapter = weathAdapter

        doWeather()
    }

    fun doWeather() {
        val url = "${apiBaseUrl}"

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<WeatherResults> =
            moshi.adapter(WeatherResults::class.java)

        val req = StringRequest(Request.Method.GET, url,
            {
                val results = jsonAdapter.fromJson(it)
                Log.d("MainActivity", it)
                weathAdapter.updateWeatherList(results?.list)
                loadingIndicator.visibility = View.INVISIBLE
                weatherResultsRV.visibility = View.VISIBLE
            },
            {
                Log.d("MainActivity", "Error fetching from $url: ${it.message}")
                loadingIndicator.visibility = View.INVISIBLE
                fetchError.visibility = View.VISIBLE
            }
        )

        loadingIndicator.visibility = View.VISIBLE
        weatherResultsRV.visibility = View.INVISIBLE
        fetchError.visibility = View.INVISIBLE
        requestQueue.add(req)
    }

    private fun onWeatherClick(forecast: ForecastPeriod) {
        val intent = Intent(this, WeatherActivity::class.java).apply {
            putExtra(EXTRA_WEATHER, forecast)
        }

        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_weather_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_view_location -> {
                viewLocation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun viewLocation() {
        val uri = Uri.parse("https://www.google.com/maps/place/Corvallis,+OR/@44.5630734,-123.3535772,12z/data=!3m1!4b1!4m5!3m4!1s0x54c0409daa14d77d:0xd70d808f22bdc0be!8m2!3d44.5645659!4d-123.2620435")
        val intent: Intent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(intent)
    }

    private data class WeatherResults(
        val list: List<ForecastPeriod>
    )
}