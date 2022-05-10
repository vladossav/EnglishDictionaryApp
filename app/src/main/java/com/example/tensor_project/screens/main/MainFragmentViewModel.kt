package com.example.tensor_project.screens.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tensor_project.api.RandomWordApi
import com.example.tensor_project.model.RandomWord
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel: ViewModel() {
    val randomWord = MutableLiveData<List<RandomWord>>()

    fun connectRandomWordApi() {
        val api = RandomWordApi.create().getWord()

        api.enqueue( object : Callback<List<RandomWord>> {
            override fun onResponse(call: Call<List<RandomWord>>?, response: Response<List<RandomWord>>?) {
                Log.i("Api",response.toString())

                if(response?.body() != null) {
                    Log.d("Api","Response got")
                    randomWord.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<RandomWord>>?, t: Throwable?) {
                Log.i("Api","some error: " + t?.message)
            }
        })
    }
}