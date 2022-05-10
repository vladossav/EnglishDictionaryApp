package com.example.tensor_project.screens.word

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tensor_project.api.DictionaryApi
import com.example.tensor_project.model.WordItem
import com.example.tensor_project.testWord.WordTest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordFragmentViewModel: ViewModel() {
    val words = MutableLiveData(WordTest.getWords())
    val wordsList = MutableLiveData<List<WordItem>>()

    fun connectApi(searchWord: String) {
        val api = DictionaryApi.create().getWord(searchWord)

        api.enqueue( object : Callback<List<WordItem>> {
            override fun onResponse(call: Call<List<WordItem>>?, response: Response<List<WordItem>>?) {
                Log.i("Api",response.toString())

                if(response?.body() != null) {
                    Log.i("Api","Response got")
                    wordsList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<WordItem>>?, t: Throwable?) {
                Log.i("Api","Error: " + t?.message)
            }
        })
    }
}