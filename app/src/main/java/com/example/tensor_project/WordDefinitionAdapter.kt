package com.example.tensor_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordDefinitionAdapter(): RecyclerView.Adapter<WordDefinitionAdapter.WordViewHolder>() {
    private val wordsKeeper = mutableListOf<Word>()

    class WordViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val partOfSpeech: TextView = item.findViewById(R.id.word_part_of_speech)
        val wordDef: TextView = item.findViewById(R.id.word_meaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_definitions_list_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.partOfSpeech.text = wordsKeeper[0].listMeans?.get(position)?.partOfSpeech.toString()
        holder.wordDef.text = wordsKeeper[0].listMeans?.get(position)?.meaning.toString()
    }

    override fun getItemCount(): Int {
        return 3
    }

    fun reload(newList: List<Word>) {
        wordsKeeper.clear()
        wordsKeeper.addAll(newList)
        notifyDataSetChanged()
    }
}