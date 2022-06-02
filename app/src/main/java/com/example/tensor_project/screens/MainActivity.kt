package com.example.tensor_project.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.tensor_project.R
import com.example.tensor_project.screens.main.MainFragment
import com.example.tensor_project.screens.saved.SavedFragment
import com.example.tensor_project.screens.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val MAIN_FRAGMENT = "MainFragment"
const val SAVED_FRAGMENT = "SavedFragment"
const val SEARCH_FRAGMENT = "SearchFragment"

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav: BottomNavigationView = findViewById(R.id.bottom_menu)
        nav.selectedItemId = R.id.menu_main

        if (savedInstanceState == null) startFragment(MAIN_FRAGMENT)
        else startFragment(viewModel.curFragment.toString())

        nav.setOnItemSelectedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            when (it.itemId) {
                R.id.menu_search -> startFragment(SEARCH_FRAGMENT)
                R.id.menu_main -> startFragment(MAIN_FRAGMENT)
                R.id.menu_saved -> startFragment(SAVED_FRAGMENT)
            }
            true
        }
    }

    override fun onBackPressed() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onBackPressed()
    }

    private fun startFragment(fragmentName: String) {
        when(fragmentName) {
            MAIN_FRAGMENT -> {
                val mainFragment: MainFragment = MainFragment.newInstance()
                startFragmentTransaction(mainFragment)
            }
            SEARCH_FRAGMENT -> {
                val searchFragment: SearchFragment = SearchFragment.newInstance()
                startFragmentTransaction(searchFragment)
            }
            SAVED_FRAGMENT -> {
                val savedFragment: SavedFragment = SavedFragment.newInstance()
                startFragmentTransaction(savedFragment)
            }
        }
        viewModel.curFragment.value = fragmentName
    }

    private fun startFragmentTransaction(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, fragment)
            .commit()
    }
}