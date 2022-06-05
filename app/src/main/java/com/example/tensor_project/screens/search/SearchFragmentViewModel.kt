package com.example.tensor_project.screens.search

import androidx.lifecycle.*
import com.example.tensor_project.room.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragmentViewModel(private val wordRepository: WordRepository): ViewModel() {
    val recentWords: LiveData<MutableList<String>> = wordRepository.recentWordsList.asLiveData()

    fun insertCurrentWord(word: String) = viewModelScope.launch(Dispatchers.IO) {
        wordRepository.insertRecentWord(word)
    }

    fun deleteAllRecentWords() = viewModelScope.launch(Dispatchers.IO) {
        wordRepository.deleteAllRecentWords()
    }

    class ViewModelFactory(private val wordRepository: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchFragmentViewModel(wordRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}