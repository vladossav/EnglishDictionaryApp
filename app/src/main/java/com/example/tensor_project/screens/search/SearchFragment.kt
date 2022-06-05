package com.example.tensor_project.screens.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tensor_project.AppInit
import com.example.tensor_project.R
import com.example.tensor_project.screens.word.WordFragment


class SearchFragment : Fragment(), RecentWordsAdapter.Listener {
    private val searchViewModel: SearchFragmentViewModel by activityViewModels() {
        SearchFragmentViewModel.ViewModelFactory((activity?.application as AppInit).repository)
    }

    override fun onClick(word: String) {
        searchViewModel.insertCurrentWord(word)
        startWordFragmentFromApi(word)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchField: SearchView = view.findViewById(R.id.search_field)
        val recentBackText: TextView  = view.findViewById(R.id.search_recently_empty_text)
        val recentTextClear: TextView = view.findViewById(R.id.search_recently_clear)
        val rv = view.findViewById<RecyclerView>(R.id.search_rv)
        val recentWordsAdapter = RecentWordsAdapter(this)
        rv.adapter = recentWordsAdapter

        searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("search","onQueryTextSubmit: " + query as String)
                searchViewModel.insertCurrentWord(query)
                startWordFragmentFromApi(query)
                return false
            }
        })

        recentTextClear.setOnClickListener {
            searchViewModel.deleteAllRecentWords()
        }

        searchViewModel.recentWords.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) recentBackText.visibility = View.GONE
            else recentBackText.visibility = View.VISIBLE
            recentWordsAdapter.reload(it)
        }

        return view
    }

    private fun startWordFragmentFromApi(query: String) {
        val bundle = Bundle()
        bundle.putString(WordFragment.WORD_FRAGMENT_KEY_FROM_API,query)
        val wordFragment = WordFragment.newInstance(bundle)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, wordFragment)
            .addToBackStack(null)
            .commit()
    }



    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}