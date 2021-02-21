package com.example.pokemonapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.observables.PokemonObservables
import kotlinx.coroutines.launch

/**
 * The view model for the pokemon info
 */
class PokemonViewModel(
    private val repo: PokemonRepository,
    private val pokemonObservables: PokemonObservables
) : AbstractViewModel() {

    /**
     * Returns the desired pokemon.
     *
     * @param id the pokemon id.
     */
    fun getPokemon(id: Int) {
        pokemonObservables.pokeLoadedObserver.value = false
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val data = repo.getPokemon(id)
            val dataModel = handleResponse(data, ::handleError)
            dataModel?.let {
                pokemonObservables.pokeDetailsObserver.value = it
                pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
                pokemonObservables.pokeLoadedObserver.value = true
            }
        }
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
    }

    /**
     * Handles any error.
     */
    private fun handleError(error: Throwable?) {
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokemonObservables.pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
        }
    }
}