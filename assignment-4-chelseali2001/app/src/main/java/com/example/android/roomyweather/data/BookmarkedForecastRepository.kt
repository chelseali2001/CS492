package com.example.android.roomyweather.data

class BookmarkedForecastRepository (private val dao: FiveDayForecastDao) {
    suspend fun insertBookmarkedRepo(repo: ForecastDB) =
        dao.insert(repo)
    fun getAllBookmarkedRepos() = dao.getAllRepos()
}