package com.example.pokemonapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.MainActivityBinding

/**
 * Pokemon app activity.
 */
class PokemonActivity : AppCompatActivity() {

    private lateinit var activityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        setSupportActionBar(activityBinding.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navigation_host_fragment).navigateUp()
    }
}