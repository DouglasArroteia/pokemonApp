package com.androidapp.douglas.pokemonapp.injection

import com.androidapp.douglas.pokemonapp.api.repositories.PokemonRepository
import com.androidapp.douglas.pokemonapp.api.repositories.PokemonRepositoryImpl
import com.androidapp.douglas.pokemonapp.persistence.SharedPreferencesHelper
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables
import com.androidapp.douglas.pokemonapp.view.viewmodel.FavoriteViewModel
import com.androidapp.douglas.pokemonapp.view.viewmodel.PokemonListViewModel
import com.androidapp.douglas.pokemonapp.view.viewmodel.PokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module related to Dependency Injection for the view models.
 */
val viewModels = module {
    viewModel { PokemonListViewModel(get(), get()) }
    viewModel { PokemonViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get(), get()) }
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

/**
 * Module related to Dependency Injection for the observables.
 */
val observables = module {
    single { PokemonObservables() }
}