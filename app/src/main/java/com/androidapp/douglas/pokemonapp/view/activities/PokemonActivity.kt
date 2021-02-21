package com.androidapp.douglas.pokemonapp.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapp.databinding.MainActivityBinding

/**
 * Pokemon app activity.
 */
class PokemonActivity : AppCompatActivity() {

    private lateinit var activityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = MainActivityBinding.inflate(layoutInflater)
        activityBinding.apply {
            setContentView(root)
            setSupportActionBar(toolbar)
        }
    }
}