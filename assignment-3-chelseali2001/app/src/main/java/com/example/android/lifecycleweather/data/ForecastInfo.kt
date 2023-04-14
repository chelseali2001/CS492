package com.example.android.lifecycleweather.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ForecastInfo (
    private val service: ForecastService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadForecastSearch(query: String?, unit: String?): Result<FiveDayForecast> =
        withContext(ioDispatcher) {
            try {
                val results = service.searchForecast(query, unit)
                Result.success(results)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}