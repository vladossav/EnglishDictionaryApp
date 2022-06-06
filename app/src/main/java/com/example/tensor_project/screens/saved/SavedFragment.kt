package com.example.tensor_project.screens.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tensor_project.AppInit
import com.example.tensor_project.R
import com.example.tensor_project.screens.word.WordFragment


class SavedFragment : Fragment(), SavedWordsAdapter.Listener {
    private val savedWordsViewModel: SavedFragmentViewModel by activityViewModels() {
        SavedFragmentViewModel.ViewModelFactory((activity?.application as AppInit).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.saved_rv)
        val textEmpty = view.findViewById<TextView>(R.id.saved_back_text)

        val savedWordsAdapter = SavedWordsAdapter(this)
        rv.adapter = savedWordsAdapter

        savedWordsViewModel.words.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) textEmpty.visibility = View.GONE
            else textEmpty.visibility = View.VISIBLE
            savedWordsAdapter.reload(it)
            rv!!.layoutManager!!.scrollToPosition(0)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = SavedFragment()
    }

    override fun onClick(word: String) {
        startWordFragmentFromDB(word)
    }

    private fun startWordFragmentFromDB(query: String) {
        val bundle = Bundle()
        bundle.putString(WordFragment.WORD_FRAGMENT_KEY_FROM_DB,query)
        val wordFragment = WordFragment.newInstance(bundle)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, wordFragment)
            .addToBackStack(null)
            .commit()
    }
}