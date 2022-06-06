package com.example.tensor_project.screens.word

import androidx.recyclerview.widget.DiffUtil
import com.example.tensor_project.model.WordItem

class WordDefinitionDiffUtil(
    private val oldList: List<WordItem.Meaning>,
    private val newList: List<WordItem.Meaning>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldList[oldItemPosition].partOfSpeech != newList[newItemPosition].partOfSpeech) return false
        return oldList[oldItemPosition].definitions.size == newList[newItemPosition].definitions.size
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        for (old in oldList[oldItemPosition].definitions) {
            for (new in newList[newItemPosition].definitions) {
                if(old.definition != new.definition) return false
            }
        }
        return true
    }
}