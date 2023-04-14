package com.example.android.connectedweather

import com.example.android.connectedweather.data.ForecastPeriod

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

const val EXTRA_WEATHER = "ForecastPeriod"

class WeatherActivity : AppCompatActivity() {
    private var forecast: ForecastPeriod? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (intent != null && intent.hasExtra(EXTRA_WEATHER)) {
            forecast = intent.getSerializableExtra(EXTRA_WEATHER) as ForecastPeriod
            findViewById<TextView>(R.id.tv_description).text =
                "Date and Time: " + forecast!!.date_time + "\n\n" +
                "Lowest Temperature: " + forecast!!.temp.tempLow + "°F\n\n" +
                "Highest Temperature: " + forecast!!.temp.tempHigh + "°F\n\n" +
                "Prob. of Precipitation: " + (forecast!!.precip * 100.00).toString() + "%\n\n" +
                "Weather: " + forecast!!.description[0].mainDescript + "\n\n" +
                "Description: " + forecast!!.description[0].fullDescript
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_weather_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_share -> {
                shareWeather()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareWeather() {
        if (forecast != null) {
            val text = getString(R.string.share_text, forecast!!.date_time, forecast!!.description[0].fullDescript, forecast!!.temp.tempLow.toString(), forecast!!.temp.tempHigh.toString(), forecast!!.precip.toString())

            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            startActivity(Intent.createChooser(intent, text))
        }
    }
}