package com.example.tensor_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var searchFragment: SearchFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var likedFragment: SavedFragment
    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav = findViewById(R.id.bottom_menu)
        nav.selectedItemId = R.id.menu_main
        mainFragment = MainFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, mainFragment)
            .commit()

        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    searchFragment = SearchFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentHolder, searchFragment)
                        .commit()
                }
                R.id.menu_main -> {
                    mainFragment = MainFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentHolder, mainFragment)
                        .commit()
                }
                R.id.menu_saved -> {
                    likedFragment = SavedFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentHolder, likedFragment)
                        .commit()
                }

            }

            true
        }


    }
}