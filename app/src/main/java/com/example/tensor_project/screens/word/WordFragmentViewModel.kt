package com.example.tensor_project.screens.word

import android.util.Log
import androidx.lifecycle.*
import com.example.tensor_project.api.DictionaryApi
import com.example.tensor_project.model.WordItem
import com.example.tensor_project.room.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordFragmentViewModel(private val wordRepository: WordRepository): ViewModel() {
    val curWordsList = MutableLiveData<List<WordItem>>()
    val isSaved = MutableLiveData(false)
    private val api = DictionaryApi.create()


    fun insertCurrentWord() = viewModelScope.launch(Dispatchers.IO) {
            wordRepository.insert(curWordsList.value!!)
        }

    fun deleteCurrentWord() = viewModelScope.launch(Dispatchers.IO) {
            wordRepository.deleteCurrentWord(curWordsList.value!![0].word)
        }

    fun getWordItemListFromDb(word: String) = viewModelScope.launch(Dispatchers.IO) {
            curWordsList.postValue(wordRepository.getWordItemList(word))
        }


    fun connectApi(searchWord: String) {
        api.getWord(searchWord).enqueue( object : Callback<List<WordItem>> {
            override fun onResponse(call: Call<List<WordItem>>?, response: Response<List<WordItem>>?) {
                Log.i("Api",response.toString())

                if(response?.body() != null) {
                    Log.i("Api","Response got")
                    curWordsList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<WordItem>>?, t: Throwable?) {
                Log.i("Api","Error: " + t?.message)
            }
        })
    }

    class ViewModelFactory(private val wordRepository: WordRepository)
        :ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordFragmentViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return WordFragmentViewModel(wordRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}