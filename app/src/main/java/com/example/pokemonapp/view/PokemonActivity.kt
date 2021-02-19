package com.example.pokemonapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.pokemonapp.R
import kotlinx.android.synthetic.main.main_activity.*

class PokemonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navigation_host_fragment).navigateUp()
    }
}