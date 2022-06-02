package com.example.tensor_project.screens.main

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tensor_project.*



class MainFragment : Fragment() {
    lateinit var sharedPref: SharedPreferences
    lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val word = view.findViewById<TextView>(R.id.word_random)
        val wordDef = view.findViewById<TextView>(R.id.word_definition_random)
        sharedPref = context!!.getSharedPreferences(RANDOM_WORD, MODE_PRIVATE)

        word.text = sharedPref.getString(RANDOM_WORD_VALUE, "Worsification")
        wordDef.text = sharedPref.getString(RANDOM_WORD_DEF, "The composition of bad poetry")

        listener = SharedPreferences.OnSharedPreferenceChangeListener { it, _ ->
            word.text = it.getString(RANDOM_WORD_VALUE, " ")
            wordDef.text = it.getString(RANDOM_WORD_DEF, " ")
        }
        sharedPref.registerOnSharedPreferenceChangeListener(listener)

        return view
    }

    override fun onPause() {
        super.onPause()
        sharedPref.unregisterOnSharedPreferenceChangeListener(listener)

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}