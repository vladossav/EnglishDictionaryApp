package com.example.tensor_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView


class WordFragment : Fragment() {
    private val wordDefinitionAdapter = WordDefinitionAdapter()
    private val wordsViewModel: SavedWordsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_word, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.word_rv)
        rv.adapter = wordDefinitionAdapter
        wordsViewModel.words.observe(this) {
            wordDefinitionAdapter.reload(it)
        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = WordFragment()
    }
}