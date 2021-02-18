package com.example.pokemonapp.loader

sealed class PokemonLoader {
    data class Loading(val isLoading: Boolean) : PokemonLoader()
    data class GenericError(val message: String) : PokemonLoader()
}