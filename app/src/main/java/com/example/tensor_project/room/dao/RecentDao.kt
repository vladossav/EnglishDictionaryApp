package com.example.tensor_project.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentDao {
    @Query("REPLACE INTO recent_words VALUES (:recentWord,datetime('now','localtime'))" )
    suspend fun insert(recentWord: String)

    @Query("DELETE FROM recent_words")
    suspend fun deleteAll()

    @Query("SELECT recent_word FROM recent_words ORDER BY last_visit DESC")
    fun getRecentWordsList(): Flow<MutableList<String>>
}