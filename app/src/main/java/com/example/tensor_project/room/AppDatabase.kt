package com.example.tensor_project.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tensor_project.room.dao.RecentDao
import com.example.tensor_project.room.dao.WordDao
import com.example.tensor_project.room.entities.RecentWordsEntity
import com.example.tensor_project.room.entities.SavedWordsEntity


@Database(version = 1, entities = [SavedWordsEntity::class, RecentWordsEntity::class], exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getRecentDao(): RecentDao
    abstract fun getWordDao(): WordDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase ?= null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dictionary_db"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                instance
            }
        }

    }
}