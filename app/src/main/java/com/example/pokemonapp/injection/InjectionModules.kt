package com.example.pokemonapp.injection

import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.api.repositories.PokemonRepositoryImpl
import com.example.pokemonapp.view.model.FavoriteViewModel
import com.example.pokemonapp.view.model.PokeModel
import com.example.pokemonapp.view.model.PokemonListViewModel
import com.example.pokemonapp.view.model.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { PokemonListViewModel(get(), get()) }
    viewModel { PokemonViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get(), get()) }
}

val repositories = module {
    single<PokemonRepository> { PokemonRepositoryImpl() }
}

val observables = module {
    single { PokeModel() }
}