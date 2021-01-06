package com.example.testcermati.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testcermati.user.data.UserGithub
import com.example.testcermati.user.data.UserGithubDao

@Database(entities = [UserGithub::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userGithubDao(): UserGithubDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "userfinder-db")
                .addCallback(object: RoomDatabase.Callback() {
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}