package com.example.pokemonapp;

import android.app.Application;
import com.example.pokemonapp.injection.observables
import com.example.pokemonapp.injection.repositories
import com.example.pokemonapp.injection.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
            viewModels,
            repositories,
            observables
        )

        startKoin {
            androidContext(this@PokemonApplication)
            modules(modules)
        }
    }
}
