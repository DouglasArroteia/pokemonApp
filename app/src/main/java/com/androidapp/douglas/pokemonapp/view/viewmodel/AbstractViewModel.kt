package com.androidapp.douglas.pokemonapp.view.viewmodel

import androidx.lifecycle.ViewModel
import com.androidapp.douglas.pokemonapp.loader.PokemonLoader
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables

/**
 * Abstract view model.
 */
abstract class AbstractViewModel : ViewModel() {
    fun <T> handleResponse(result: Result<T>, pokemonObservables: PokemonObservables): T? {
        return if (result.isFailure) {
            handleError(result.exceptionOrNull(), pokemonObservables)
            null
        } else {
            result.getOrNull()
        }
    }

    private fun handleError(error: Throwable?, pokemonObservables: PokemonObservables) {
        pokemonObservables.apply {
            pokeLoaderObserver.value = PokemonLoader.Loading(false)
            error?.message?.let {
                pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
            }
        }
    }
}