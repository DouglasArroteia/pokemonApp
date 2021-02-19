package com.example.pokemonapp.loader

/**
 * Handles the loading.
 */
sealed class PokemonLoader {
    data class Loading(val isLoading: Boolean) : PokemonLoader()
    data class GenericError(val message: String) : PokemonLoader()
}