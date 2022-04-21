package com.example.tensor_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedWordsViewModel: ViewModel() {
    val words = MutableLiveData(Word.getWords())
}