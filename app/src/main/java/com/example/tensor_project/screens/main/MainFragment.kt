package com.example.tensor_project.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.tensor_project.R


class MainFragment : Fragment() {
    private val viewModel: MainFragmentViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val word = view.findViewById<TextView>(R.id.word_random)
        val wordDef = view.findViewById<TextView>(R.id.word_definition_random)

        viewModel.randomWord.observe(viewLifecycleOwner, {
            word.text = it.get(0).word
            wordDef.text = it.get(0).definition
        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}