package com.example.tensor_project.screens.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tensor_project.R
import com.example.tensor_project.screens.word.WordFragmentViewModel


class SavedFragment : Fragment() {
    private val wordsViewModel: WordFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.saved_rv)
        val savedWordsAdapter = SavedWordsAdapter()
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