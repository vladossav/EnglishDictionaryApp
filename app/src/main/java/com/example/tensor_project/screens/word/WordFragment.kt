package com.example.tensor_project.screens.word

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tensor_project.R
import com.example.tensor_project.model.WordItem
import com.example.tensor_project.screens.MAIN_FRAGMENT

class WordFragment : Fragment() {
    private val wordsViewModel: WordFragmentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val str = arguments?.getString(WORD_FRAGMENT_KEY)
        Log.d("Api",str!!)
        if (savedInstanceState == null) wordsViewModel.connectApi(str)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_word, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val word = view.findViewById<TextView>(R.id.word)
        val phonetic = view.findViewById<TextView>(R.id.word_phonetic)
        val wordDefinitionAdapter = WordDefinitionAdapter()

        wordsViewModel.wordsList.observe(viewLifecycleOwner, {
            Log.d("Api",it.size.toString())
            var arr: ArrayList<WordItem.Meaning> = arrayListOf()
            for (element in it) {
                arr.addAll(element.meanings)
            }

            wordDefinitionAdapter.reload(arr)

            //word
            word.text = it?.get(0)?.word
            //phonetic
            if (it?.get(0)?.phonetic == "") phonetic.setLines(0)
            else phonetic.text = it?.get(0)?.phonetic
        })

        val rv = view.findViewById<RecyclerView>(R.id.word_rv)
        rv.adapter = wordDefinitionAdapter

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.word_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
                activity?.supportFragmentManager?.popBackStack()
            }
            R.id.word_toolbar_save -> {
                if (item.isChecked) {
                    item.setChecked(false)
                    item.setIcon(R.drawable.menu_saved)
                }
                else {
                    item.setChecked(true)
                    item.setIcon(R.drawable.word_toolbar_save)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val WORD_FRAGMENT_KEY = "WORD_FRAGMENT_KEY"
        @JvmStatic
        fun newInstance(args: Bundle?): WordFragment {
            val fragment = WordFragment()
            fragment.arguments = args
            return fragment
        }
    }
}