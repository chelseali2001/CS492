package com.example.android.roomyweather.data

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.Database

const val DATABASE_NAME = "forecast-db"

@Database(entities = [ForecastDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fiveDayForecastDao(): FiveDayForecastDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}