package com.example.tensor_project.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.tensor_project.*
import com.google.android.material.card.MaterialCardView


class MainFragment : Fragment() {
    private val mainViewModel: MainFragmentViewModel by activityViewModels() {
        MainFragmentViewModel.ViewModelFactory((activity?.application as AppInit).repository, (activity?.application as AppInit).sharedPref)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val word = view.findViewById<TextView>(R.id.word_random)
        val wordDef = view.findViewById<TextView>(R.id.word_definition_random)
        val progressBar: ProgressBar = view.findViewById(R.id.main_loading_word)
        val wordCard = view.findViewById<MaterialCardView>(R.id.main_card)
        val animWord = AnimationUtils.loadAnimation(context, R.anim.tv_appearance)

        wordCard.startAnimation(animWord)

        mainViewModel.loading.observe(viewLifecycleOwner, {
            progressBar.isVisible = it
        })

        mainViewModel.curWord.observe(viewLifecycleOwner, {
            wordCard.startAnimation(animWord)
            word.text = it.word
            wordDef.text = it.definition
        })

        mainViewModel.errorDetected.observe(viewLifecycleOwner, {
            if (it) Toast.makeText(context, mainViewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
        })

        val animRotate: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        val btnRefresh = view.findViewById<ImageButton>(R.id.main_refresh_btn)
        btnRefresh.setOnClickListener {
            it.startAnimation(animRotate)
            mainViewModel.connectRandomWordApi()
        }
        return view
    }

    override fun onPause() {
        mainViewModel.saveWordToDb()
        super.onPause()
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}