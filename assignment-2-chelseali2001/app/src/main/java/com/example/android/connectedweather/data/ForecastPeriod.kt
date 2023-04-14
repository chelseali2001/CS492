package com.example.android.connectedweather.data

import android.view.accessibility.AccessibilityWindowInfo
import com.squareup.moshi.Json
import java.io.Serializable

data class ForecastPeriod(
    @Json(name = "dt_txt") val date_time: String,
    @Json(name = "main") val temp: Temperature,
    @Json(name = "pop") val precip: Int,
    @Json(name = "weather") val description: List<LongDescription>
) : Serializable

data class Temperature(
    @Json(name = "temp_min") val tempLow: Double,
    @Json(name = "temp_max") val tempHigh: Double
) : Serializable

data class LongDescription(
    @Json(name = "main") val mainDescript: String,
    @Json(name = "description") val fullDescript: String
) : Serializable