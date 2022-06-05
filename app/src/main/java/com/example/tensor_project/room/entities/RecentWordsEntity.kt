package com.example.tensor_project.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_words")
data class RecentWordsEntity(
    @PrimaryKey
    @ColumnInfo(name = "recent_word")
    val word: String,
    @ColumnInfo(name = "last_visit")
    val lastRequestTime: String
)
