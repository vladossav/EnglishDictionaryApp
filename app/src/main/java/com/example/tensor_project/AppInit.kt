package com.example.tensor_project

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.tensor_project.api.RandomWordApi
import com.example.tensor_project.model.RandomWord
import com.example.tensor_project.room.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val RANDOM_WORD = "RANDOM_WORD"
const val RANDOM_WORD_VALUE = "wordFromApi"
const val RANDOM_WORD_DEF = "defOfWordFromApi"

class AppInit:Application() {
    val api = RandomWordApi.create()

    override fun onCreate() {
        super.onCreate()
        connectRandomWordApi()

    }

    fun connectRandomWordApi() {
        api.getWord().enqueue( object : Callback<List<RandomWord>> {
            override fun onResponse(call: Call<List<RandomWord>>?, response: Response<List<RandomWord>>?) {
                Log.i("Api",response.toString())

                if(response?.body() != null) {
                    Log.d("Api","Response got")
                    val res = response.body()
                    val word: RandomWord = res!!.first()
                    val sharedPref = getSharedPreferences(RANDOM_WORD, Context.MODE_PRIVATE)
                    sharedPref.edit()
                        .putString(RANDOM_WORD_VALUE, word.word)
                        .putString(RANDOM_WORD_DEF, word.definition)
                        .apply()

                    Log.d("Api",sharedPref.getString(RANDOM_WORD_VALUE,"nothing").toString())
                    Log.d("Api",sharedPref.getString(RANDOM_WORD_DEF,"nothing").toString())
                }
            }

            override fun onFailure(call: Call<List<RandomWord>>?, t: Throwable?) {
                Log.i("Api","some error: " + t?.message)
            }
        })
    }
}