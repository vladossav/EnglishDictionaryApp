package com.example.tensor_project.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tensor_project.room.entities.SavedWordsEntity

@Database(version = 1, entities = [SavedWordsEntity::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun getWordDao(): WordDao
    
    companion object {
        private var instance: AppDatabase ?= null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return if (instance == null) {
                instance = Room.databaseBuilder(context,
                AppDatabase::class.java,"dictionary_db").build()
                instance as AppDatabase

            }  else {
                instance as AppDatabase
            }
        }
    }
}