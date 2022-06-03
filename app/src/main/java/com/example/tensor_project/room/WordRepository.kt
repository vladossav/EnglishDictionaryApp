package com.example.tensor_project.room

import com.example.tensor_project.model.WordItem
import com.example.tensor_project.room.entities.SavedWordsEntity
import com.example.tensor_project.room.entities.WordJsonConverter
import kotlinx.coroutines.flow.*

class WordRepository(private val wordDao: WordDao) {
    val wordsList: Flow<MutableList<String>> = wordDao.getSavedWordsList()

    suspend fun insert(wordsList: List<WordItem>) {
        val word = wordsList[0].word
        val str = WordJsonConverter().toJson(wordsList)
        val wordToDb = SavedWordsEntity(word, str)
        wordDao.insert(wordToDb)
    }

    suspend fun deleteCurrentWord(word: String) {
        wordDao.delete(word)
    }

    suspend fun getWordItemList(word: String): List<WordItem> {
        val wordFromDb = wordDao.getSavedWordItemList(word)
        val json = wordFromDb.wordInfo
        val list = WordJsonConverter().fromJson(json)
        return list
    }

}