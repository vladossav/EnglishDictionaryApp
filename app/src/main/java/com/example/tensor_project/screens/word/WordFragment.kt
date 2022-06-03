package com.example.tensor_project.screens.word

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tensor_project.AppInit
import com.example.tensor_project.R
import com.example.tensor_project.model.WordItem

class WordFragment : Fragment() {
    private val wordsViewModel: WordFragmentViewModel by activityViewModels {
        WordFragmentViewModel.ViewModelFactory((activity?.application as AppInit).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            var str: String? = null
            if(arguments!!.containsKey(WORD_FRAGMENT_KEY_FROM_API)) {
                str = arguments?.getString(WORD_FRAGMENT_KEY_FROM_API)
                wordsViewModel.connectApi(str!!)
                wordsViewModel.isSaved.value = false
            }
            if(arguments!!.containsKey(WORD_FRAGMENT_KEY_FROM_DB)) {
                str = arguments?.getString(WORD_FRAGMENT_KEY_FROM_DB)
                wordsViewModel.getWordItemListFromDb(str!!)
                wordsViewModel.isSaved.value = true
            }
            Log.d("Api",str!!)
        }
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

        wordsViewModel.curWordsList.observe(viewLifecycleOwner, {
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

        val el = menu.findItem(R.id.word_toolbar_save)

        el.isChecked = wordsViewModel.isSaved.value!!
        if (el.isChecked) el.setIcon(R.drawable.word_toolbar_save)

    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
                activity?.supportFragmentManager?.popBackStack()
            }
            R.id.word_toolbar_save -> {
                if (item.isChecked) {
                    wordsViewModel.deleteCurrentWord()
                    Toast.makeText(context, "Word was deleted from your saved list!",Toast.LENGTH_SHORT).show()
                    item.isChecked = false
                    item.setIcon(R.drawable.menu_saved)
                }
                else {
                    wordsViewModel.insertCurrentWord()
                    Toast.makeText(context, "Word was added to your saved list!",Toast.LENGTH_SHORT).show()
                    item.isChecked = true
                    item.setIcon(R.drawable.word_toolbar_save)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val WORD_FRAGMENT_KEY_FROM_API = "WORD_FRAGMENT_KEY"
        const val WORD_FRAGMENT_KEY_FROM_DB = "WORD_FRAGMENT_KEY_DB"
        @JvmStatic
        fun newInstance(args: Bundle?): WordFragment {
            val fragment = WordFragment()
            fragment.arguments = args
            return fragment
        }
    }
}