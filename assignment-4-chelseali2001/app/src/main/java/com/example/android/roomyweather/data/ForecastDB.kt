package com.example.android.roomyweather.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ForecastDB (
    @PrimaryKey
    val city: String,
    val timestamp: String
) : Serializable