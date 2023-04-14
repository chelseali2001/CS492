package com.example.android.basicweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    val weather: MutableList<Weather> = mutableListOf(Weather("Jan", "25", "49℉", "32℉", "9% precip.", "Partly Cloudy", "Partly cloudy all day"),
                                                      Weather("Jan", "26", "49℉", "32℉", "9% precip.", "Partly Cloudy", "Partly cloudy with light wind all day"),
                                                      Weather("Jan", "27", "49℉", "33℉", "11% precip.", "Mostly Sunny", "Mostly sunny throughout the day"),
                                                      Weather("Jan", "28", "49℉", "37℉", "23% precip.", "Partly Cloudy", "Partly cloudy in the evening"),
                                                      Weather("Jan", "29", "48℉", "37℉", "24% precip.", "Cloudy", "Overcast with chance of rain"),
                                                      Weather("Jan", "30", "47℉", "39℉", "37% precip.", "Showers", "Cloudy with occastional showers"),
                                                      Weather("Jan", "31", "51℉", "43℉", "25% precip.", "Mostly Sunny", "Mostly sunny with clouds increasing in the afternoon"),
                                                      Weather("Feb", "1", "55℉", "39℉", "80% precip.", "AM Showers", "Chance of rain in the morning"),
                                                      Weather("Feb", "2", "47℉", "39℉", "10% precip.", "AM fog/PM clouds", "Fog in the morning with cloudy skies in the evening"),
                                                      Weather("Feb", "3", "53℉", "36℉", "60% precip.", "AM Showers", "Chance of rain in the morning")
                                                     )

    override fun getItemCount() = this.weather.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.weather[position])

        holder.itemView.setOnClickListener {
            val snackbar = Snackbar.make(holder.itemView, "${weather[position].longDescript}", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val weatherMonth: TextView = view.findViewById(R.id.tv_month)
        private val weatherDay: TextView = view.findViewById(R.id.tv_day)
        private val weatherHighTemp: TextView = view.findViewById(R.id.tv_hightemp)
        private val weatherLowTemp: TextView = view.findViewById(R.id.tv_lowtemp)
        private val weatherProbPercip: TextView = view.findViewById(R.id.tv_probpercip)
        private val weatherDescript: TextView = view.findViewById(R.id.tv_descript)

        fun bind(forcast: Weather) {
            this.weatherMonth.text = forcast.month
            this.weatherDay.text = forcast.day
            this.weatherHighTemp.text = forcast.highTemp
            this.weatherLowTemp.text = forcast.lowTemp
            this.weatherProbPercip.text = forcast.probPercip
            this.weatherDescript.text = forcast.descript
        }
    }
}