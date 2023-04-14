package com.example.android.roomyweather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomyweather.data.*
import kotlinx.coroutines.launch

class BookmarkedReposViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookmarkedForecastRepository(
        AppDatabase.getInstance(application).fiveDayForecastDao()
    )

    val bookmarkedRepos = repository.getAllBookmarkedRepos().asLiveData()

    fun addBookmarkedRepo(repo: ForecastDB) {
        viewModelScope.launch {
            repository.insertBookmarkedRepo(repo)
        }
    }
}