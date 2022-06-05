package com.example.tensor_project.room.entities


import androidx.room.*
import com.example.tensor_project.model.WordItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity(tableName = "saved_words")
data class SavedWordsEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "word_info")
    val wordInfo: String, //JSON
)

class WordJsonConverter {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()
    private val type = Types.newParameterizedType(List::class.java, WordItem::class.java)
    private val jsonAdapter = moshi.adapter<List<WordItem>>(type)

    fun fromJson(string: String): List<WordItem> {
        return jsonAdapter.fromJson(string).orEmpty()
    }

    fun toJson(word: List<WordItem>): String {
        return jsonAdapter.toJson(word)
    }
}
