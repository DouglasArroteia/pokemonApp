package com.androidapp.douglas.pokemonapp.loader

/**
 * Handles the loading.
 */
sealed class PokemonLoader {
    data class Loading(val isLoading: Boolean) : PokemonLoader()
    data class DefaultError(val message: String) : PokemonLoader()
}