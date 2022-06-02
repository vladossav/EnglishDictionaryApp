package com.example.tensor_project.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_words")
data class RecentWordsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word: String
)
