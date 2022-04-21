package com.example.tensor_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class SearchFragment : Fragment() {
    private lateinit var wordFragment: WordFragment
    private lateinit var testBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view = inflater.inflate(R.layout.fragment_search, container, false)

        testBtn = view.findViewById(R.id.testButton)
        testBtn.setOnClickListener {
            wordFragment = WordFragment.newInstance()
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentHolder, wordFragment)
                .commit()
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}