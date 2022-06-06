package com.example.tensor_project

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.tensor_project.room.AppDatabase
import com.example.tensor_project.room.WordRepository


const val RANDOM_WORD = "RANDOM_WORD"
const val RANDOM_WORD_VALUE = "wordFromApi"
const val RANDOM_WORD_DEF = "defOfWordFromApi"

class AppInit: Application() {
    private val database by lazy {
        AppDatabase.getInstance(this)
    }
    val repository by lazy { WordRepository(database.getWordDao(), database.getRecentDao()) }
    val sharedPref: SharedPreferences by lazy { getSharedPreferences(RANDOM_WORD, Context.MODE_PRIVATE)}
}