package com.example.tensor_project.room

import androidx.room.*
import com.example.tensor_project.room.entities.SavedWordsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedWordsEntity: SavedWordsEntity)

    @Query("DELETE FROM saved_words WHERE word = :str")
    suspend fun delete(str: String)

    @Query("SELECT * FROM saved_words")
    suspend fun getAllSavedWords(): List<SavedWordsEntity>

    @Query("SELECT word FROM saved_words")
    fun getSavedWordsList(): Flow<MutableList<String>>

    @Query("SELECT * FROM saved_words WHERE word = :wordValue")
    suspend fun getSavedWordItemList(wordValue: String): SavedWordsEntity
}