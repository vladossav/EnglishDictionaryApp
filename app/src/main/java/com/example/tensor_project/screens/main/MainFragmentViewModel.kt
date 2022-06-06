package com.example.tensor_project.screens.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tensor_project.RANDOM_WORD_DEF
import com.example.tensor_project.RANDOM_WORD_VALUE
import com.example.tensor_project.api.RandomWordApi
import com.example.tensor_project.model.RandomWord
import com.example.tensor_project.room.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


class MainFragmentViewModel(private val wordRepository: WordRepository,private val shPref: SharedPreferences) :
    ViewModel() {

    val curWord = MutableLiveData<RandomWord>()
    val errorDetected = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData(false)
    private val api = RandomWordApi.create()

    init {
        val str1 = shPref.getString(RANDOM_WORD_VALUE, "Worsification")!!
        val str2 = shPref.getString(RANDOM_WORD_DEF, "The composition of bad poetry")!!
        curWord.value = RandomWord(str2,str1)
    }

    fun connectRandomWordApi() = viewModelScope.launch(Dispatchers.IO) {
        errorDetected.postValue(false)
        loading.postValue(true)

        val response = try {
            api.getWordResponse()
        } catch (e: IOException) {
            errorMessage.postValue("Error: Internet connection is corrupted!")
            errorDetected.postValue(true)
            Log.e("Api", "IOException, you might not have internet connection")
            loading.postValue(false)
            return@launch
        } catch (e: HttpException) {
            errorMessage.postValue("Error: Internet connection is corrupted!")
            errorDetected.postValue(true)
            Log.e("Api", "HttpException, unexpected response")
            loading.postValue(false)
            return@launch
        } catch (e: Exception) {
            loading.postValue(false)
            errorMessage.postValue("Error: Some troubles on server! Try later!")
            errorDetected.postValue(true)
            Log.e("Api","Error: Some troubles on server! Try later!")
            return@launch
        }

        if (response.isSuccessful && response.body() != null) {
            val res = response.body()
            val word: RandomWord = res!!.first()

            Log.d("Api", response.message())
            curWord.postValue(word)
            errorDetected.postValue(false)
        } else {
            Log.e("Api", "Response not successful")
            errorMessage.postValue("Random word cannot be found! Empty response of server")
            errorDetected.postValue(true)
        }

        loading.postValue(false)
    }

    fun saveWordToDb() = viewModelScope.launch(Dispatchers.IO) {
        shPref.edit()
            .putString(RANDOM_WORD_VALUE, curWord.value!!.word)
            .putString(RANDOM_WORD_DEF, curWord.value!!.definition)
            .apply()
    }


    class ViewModelFactory(private val wordRepository: WordRepository, private val shPref: SharedPreferences) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainFragmentViewModel(wordRepository, shPref) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
