package com.androidapp.douglas.pokemonapp;

import android.app.Application;
import com.androidapp.douglas.pokemonapp.injection.observables
import com.androidapp.douglas.pokemonapp.injection.persistence
import com.androidapp.douglas.pokemonapp.injection.repositories
import com.androidapp.douglas.pokemonapp.injection.viewModels
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
            persistence,
            observables
        )

        startKoin {
            androidContext(this@PokemonApplication)
            modules(modules)
        }
    }
}
