package com.example.tensor_project.screens.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.tensor_project.room.WordRepository


class SavedFragmentViewModel(private val wordRepository: WordRepository):
ViewModel() {
    val words: LiveData<MutableList<String>> = wordRepository.wordsList.asLiveData()

    class ViewModelFactory(private val wordRepository: WordRepository)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SavedFragmentViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return SavedFragmentViewModel(wordRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}