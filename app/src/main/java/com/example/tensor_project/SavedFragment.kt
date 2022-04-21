package com.example.tensor_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SavedFragment : Fragment() {
    private val savedWordsAdapter = SavedWordsAdapter()
    private val wordsViewModel: SavedWordsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.saved_rv)
        rv.adapter = savedWordsAdapter
        wordsViewModel.words.observe(this) {
            savedWordsAdapter.reload(it)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = SavedFragment()
    }
}