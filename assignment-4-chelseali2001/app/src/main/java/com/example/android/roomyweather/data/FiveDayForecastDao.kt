package com.example.android.roomyweather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FiveDayForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: ForecastDB)

    @Query("SELECT * FROM ForecastDB")
    fun getAllRepos(): Flow<List<ForecastDB>>
}