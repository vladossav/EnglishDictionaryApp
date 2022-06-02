package com.example.tensor_project.room

import androidx.room.*
import com.example.tensor_project.room.entities.SavedWordsEntity

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedWordsEntity: SavedWordsEntity)

    @Delete
    suspend fun delete(savedWordsEntity: SavedWordsEntity)

    @Query("SELECT * FROM saved_words")
    suspend fun getAllSavedWords(): List<SavedWordsEntity>
}