package com.example.android.connectedweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.example.android.connectedweather.data.ForecastPeriod
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(var forecastPeriods: List<ForecastPeriod>, private val onWeatherClick: (ForecastPeriod) -> Unit) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    fun updateWeatherList(newWeatherList: List<ForecastPeriod>?) {
        forecastPeriods = newWeatherList ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.forecastPeriods.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_list_item, parent, false)
        return ViewHolder(view, onWeatherClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.forecastPeriods[position])
    }

    class ViewHolder(view: View, val onClick: (ForecastPeriod) -> Unit) : RecyclerView.ViewHolder(view) {
        private val dateTV: TextView = view.findViewById(R.id.tv_date)
        private val timeTV: TextView = view.findViewById(R.id.tv_time)
        private val highTempTV: TextView = view.findViewById(R.id.tv_high_temp)
        private val lowTempTV: TextView = view.findViewById(R.id.tv_low_temp)
        private val shortDescTV: TextView = view.findViewById(R.id.tv_short_description)
        private val popTV: TextView = view.findViewById(R.id.tv_pop)

        private var currentForecastPeriod: ForecastPeriod? = null

        init {
            view.setOnClickListener {
                currentForecastPeriod?.let(onClick)
            }
        }

        fun bind(forecastPeriod: ForecastPeriod) {
            currentForecastPeriod = forecastPeriod

            val cal = Calendar.getInstance()

            val fp_dt_txt = forecastPeriod.date_time.split("-", " ")

            cal.set(fp_dt_txt[0].toInt(), fp_dt_txt[1].toInt(), fp_dt_txt[2].toInt())
            val dateVal = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " + cal.get(Calendar.DAY_OF_MONTH).toString()

            dateTV.text = dateVal
            timeTV.text = fp_dt_txt[3]
            highTempTV.text = forecastPeriod.temp.tempHigh.toString() + "°F"
            lowTempTV.text = forecastPeriod.temp.tempLow.toString() + "°F"
            popTV.text = (forecastPeriod.precip * 100.0).toInt().toString() + "% precip."
            shortDescTV.text = forecastPeriod.description[0].mainDescript
        }
    }
}