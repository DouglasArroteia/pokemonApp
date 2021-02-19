package com.example.pokemonapp.injection

import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.api.repositories.PokemonRepositoryImpl
import com.example.pokemonapp.persistence.SharedPreferencesHelper
import com.example.pokemonapp.view.LoadingDialog
import com.example.pokemonapp.view.model.FavoriteViewModel
import com.example.pokemonapp.view.model.PokeModel
import com.example.pokemonapp.view.model.PokemonListViewModel
import com.example.pokemonapp.view.model.PokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonViewModel(get(), androidContext()) }
    viewModel { FavoriteViewModel(get()) }
}

val repositories = module {
    single<PokemonRepository> { PokemonRepositoryImpl() }
}

val persistence = module {
    single { SharedPreferencesHelper(androidContext()) }
}