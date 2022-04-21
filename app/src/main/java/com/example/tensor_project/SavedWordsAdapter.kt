package com.example.tensor_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SavedWordsAdapter() : RecyclerView.Adapter<SavedWordsAdapter.WordViewHolder>() {
    private val wordsKeeper = mutableListOf<Word>()

    class WordViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val word: TextView = item.findViewById(R.id.saved_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_list_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        holder.word.text = wordsKeeper[position].word
    }

    override fun getItemCount(): Int {
        return wordsKeeper.size
    }

    fun reload(newList: List<Word>) {
        wordsKeeper.clear()
        wordsKeeper.addAll(newList)
        notifyDataSetChanged()
    }
}