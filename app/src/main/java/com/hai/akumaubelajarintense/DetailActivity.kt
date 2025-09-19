package com.hai.akumaubelajarintense

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailActivity : AppCompatActivity() {

    private var currentUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        currentUsername = intent.getStringExtra("EXTRA_USERNAME")

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            // Muat HomeFragment dengan argumen username
            val initialHomeFragment = HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("USERNAME_ARG", currentUsername)
                }
            }
            loadFragment(initialHomeFragment)
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> {
                    selectedFragment = HomeFragment().apply {
                        arguments = Bundle().apply {
                            putString("USERNAME_ARG", currentUsername) // Kirim username ke HomeFragment
                        }
                    }
                }
                R.id.navigation_profile -> {
                    selectedFragment = ProfileFragment().apply {
                        arguments = Bundle().apply {
                            putString("USERNAME_ARG", currentUsername)
                        }
                    }
                }
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment)
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}