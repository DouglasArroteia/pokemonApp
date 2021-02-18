package com.example.pokemonapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapp.R
import kotlinx.android.synthetic.main.main_activity.*

class PokemonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
    }
}