package com.example.pokemonapp;

import android.app.Application;
import com.example.pokemonapp.injection.persistence
import com.example.pokemonapp.injection.repositories
import com.example.pokemonapp.injection.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class
 */
class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
            viewModels,
            repositories,
            persistence
        )

        startKoin {
            androidContext(this@PokemonApplication)
            modules(modules)
        }
    }
}
