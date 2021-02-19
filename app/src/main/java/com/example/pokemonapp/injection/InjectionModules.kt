package com.example.pokemonapp.injection

import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.api.repositories.PokemonRepositoryImpl
import com.example.pokemonapp.persistence.SharedPreferencesHelper
import com.example.pokemonapp.view.model.FavoriteViewModel
import com.example.pokemonapp.view.model.PokemonListViewModel
import com.example.pokemonapp.view.model.PokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module related to Dependency Injection for the view models.
 */
val viewModels = module {
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonViewModel(get(), androidContext()) }
    viewModel { FavoriteViewModel(get()) }
}

/**
 * Module related to Dependency Injection for the repositories.
 */
val repositories = module {
    single<PokemonRepository> { PokemonRepositoryImpl() }
}

/**
 * Module related to Dependency Injection for the persistence data.
 */
val persistence = module {
    single { SharedPreferencesHelper(androidContext()) }
}