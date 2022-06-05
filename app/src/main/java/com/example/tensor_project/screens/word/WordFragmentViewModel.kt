package com.example.tensor_project.screens.word

import android.util.Log
import androidx.lifecycle.*
import com.example.tensor_project.api.DictionaryApi
import com.example.tensor_project.model.WordItem
import com.example.tensor_project.room.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WordFragmentViewModel(private val wordRepository: WordRepository): ViewModel() {
    val curWordsList = MutableLiveData<List<WordItem>>()
    val isSaved = MutableLiveData(false)
    val errorDetected = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData(false)
    private val api = DictionaryApi.create()


    fun insertCurrentWord() = viewModelScope.launch(Dispatchers.IO) {
            wordRepository.insert(curWordsList.value!!)
        }

    fun deleteCurrentWord() = viewModelScope.launch(Dispatchers.IO) {
            wordRepository.deleteCurrentWord(curWordsList.value!![0].word)
        }

    fun getWordItemListFromDb(word: String) = viewModelScope.launch(Dispatchers.IO) {
            errorDetected.postValue(false)
            loading.postValue(true)
            curWordsList.postValue(wordRepository.getWordItemList(word))
            loading.postValue(false)
        }

    fun connectApi(searchWord: String) = viewModelScope.launch(Dispatchers.IO) {
        errorDetected.postValue(false)
        loading.postValue(true)
        if (wordRepository.checkSavedWord(searchWord)) {
            getWordItemListFromDb(searchWord)
            isSaved.postValue(true)
            return@launch
        }

        val response = try {
            api.getWord(searchWord)
        } catch (e: IOException) {
            errorMessage.postValue("Error: Internet connection is corrupted!")
            Log.e("Api", "IOException, you might not have internet connection")
            errorDetected.postValue(true)
            loading.postValue(false)
            return@launch
        } catch (e: HttpException) {
            errorMessage.postValue("Error: Internet connection is corrupted!")
            Log.e("Api", "HttpException, unexpected response")
            errorDetected.postValue(true)
            loading.postValue(false)
            return@launch
        }

        if (response.isSuccessful && response.body() != null) {
            curWordsList.postValue(response.body())
            errorDetected.postValue(false)
        } else {
            errorDetected.postValue(true)
            Log.e("Api", "Response not successful")
        }

        loading.postValue(false)
    }



    class ViewModelFactory(private val wordRepository: WordRepository)
        :ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WordFragmentViewModel(wordRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}