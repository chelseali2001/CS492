package com.example.android.lifecycleweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.lifecycleweather.data.FiveDayForecast
import com.example.android.lifecycleweather.data.ForecastInfo
import com.example.android.lifecycleweather.data.ForecastService
import com.example.android.lifecycleweather.data.LoadingStatus
import kotlinx.coroutines.launch

class ForecastViewModel: ViewModel() {
    private val forecast = ForecastInfo(ForecastService.create())

    private val _searchResults = MutableLiveData<FiveDayForecast?>(null)
    val searchResults: LiveData<FiveDayForecast?> = _searchResults

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadSearchResults(query: String?, unit: String?) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = forecast.loadForecastSearch(query, unit)
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}