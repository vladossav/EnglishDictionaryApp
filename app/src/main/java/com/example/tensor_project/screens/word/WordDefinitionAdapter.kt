package com.example.tensor_project.screens.word

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tensor_project.R
import com.example.tensor_project.model.WordItem

class WordDefinitionAdapter(): RecyclerView.Adapter<WordDefinitionAdapter.WordViewHolder>() {
    private var wordsKeeper: ArrayList<WordItem.Meaning> = arrayListOf()

    class WordViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val partOfSpeech: TextView = item.findViewById(R.id.word_part_of_speech)
        val card: LinearLayout = item.findViewById(R.id.word_def_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_definitions_list_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.partOfSpeech.text = wordsKeeper[position].partOfSpeech
        holder.card.removeAllViewsInLayout()

        var i = 1
        for (def in wordsKeeper[position].definitions) {
            val view = LayoutInflater.from(holder.card.context).inflate(R.layout.word_definitions_card, null, false)
            val wordDef: TextView = view.findViewById(R.id.word_meaning)
            val wordSynonyms: TextView = view.findViewById(R.id.word_synonyms)
            val wordSynonymsText: TextView = view.findViewById(R.id.word_synonyms_text)
            val wordAntonyms: TextView = view.findViewById(R.id.word_antonyms)
            val wordAntonymsText: TextView = view.findViewById(R.id.word_antonyms_text)
            val wordExample: TextView = view.findViewById(R.id.word_example)
            val wordExampleText: TextView = view.findViewById(R.id.word_example_text)

            //definition
            val strDef = "$i) " + def.definition
            wordDef.text = strDef
            i++

            //synonyms
            var strSyn = ""
            for (syn in def.synonyms) strSyn = "$strSyn, $syn"
            strSyn = strSyn.drop(1)
            if (strSyn == "") {
                wordSynonyms.setLines(0)
                wordSynonymsText.setLines(0)
            }
            else wordSynonymsText.text = strSyn

            //antonyms
            var strAnt = ""
            for (ant in def.antonyms) strAnt = "$strAnt, $ant"
            strAnt = strAnt.drop(1)
            if (strAnt == "") {
                wordAntonyms.setLines(0)
                wordAntonymsText.setLines(0)
            }
            else wordAntonymsText.text = strAnt

            //examples
            if (def.example == "") {
                wordExample.setLines(0)
                wordExampleText.setLines(0)
            }
            else wordExampleText.text = def.example

            holder.card.addView(view)
        }

    }

    override fun getItemCount(): Int {
        return wordsKeeper.size
    }

    fun reload(word: List<WordItem.Meaning>) {
        Log.d("Api","reload")
        val difResult = DiffUtil.calculateDiff(WordDefinitionDiffUtil(wordsKeeper, word))
        wordsKeeper.clear()
        wordsKeeper.addAll(word)
        difResult.dispatchUpdatesTo(this)
    }
}