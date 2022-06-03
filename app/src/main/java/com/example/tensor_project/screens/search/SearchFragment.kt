package com.example.tensor_project.screens.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import com.example.tensor_project.R
import com.example.tensor_project.screens.word.WordFragment


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val searchField: SearchView = view.findViewById(R.id.search_field)

        searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("search","onQueryTextSubmit: " + query as String)
                startWordFragmentFromApi(query)
                return false
            }
        })

        val testList = listOf(
            "hello","go","text","take","bright"
        )
        val testBtn: Button = view.findViewById(R.id.search_testButton)
        testBtn.setOnClickListener {

            startWordFragmentFromApi(testList.random())
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