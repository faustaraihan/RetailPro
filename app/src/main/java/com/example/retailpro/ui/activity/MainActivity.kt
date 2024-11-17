package com.example.retailpro.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.retailpro.R
import com.example.retailpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setupBottomNavigationView()
        setupButton()
    }

    private fun setupButton() {
        binding.buttonSearch.setOnClickListener{
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    
    private fun setupBottomNavigationView() {
        val navigationView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    binding.toolbarText.visibility = View.GONE
                    binding.buttonSearch.visibility = View.VISIBLE
                }
                R.id.navigation_favorite -> {
                    binding.toolbarText.visibility = View.VISIBLE
                    binding.toolbarText.text = resources.getString(R.string.title_favorite)
                    binding.buttonSearch.visibility = View.GONE
                }
                R.id.navigation_about -> {
                    binding.toolbarText.visibility = View.VISIBLE
                    binding.toolbarText.text = resources.getString(R.string.title_about)
                    binding.buttonSearch.visibility = View.GONE
                }
            }
        }
    }
}