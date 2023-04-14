package com.example.android.lifecycleweather.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface ForecastService {
    @GET("forecast")
    suspend fun searchForecast (
        @Query("q") query: String?,
        @Query("units") unit: String?,
        @Query("appid") apiid: String = "6ea62b7d1ad89ea0dd964e49ffb0b7b7"
    ) : FiveDayForecast

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        fun create() : ForecastService {
            val moshi = Moshi.Builder()
                .add(OpenWeatherListJsonAdapter())
                .add(OpenWeatherCityJsonAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(ForecastService::class.java)
        }
    }
}