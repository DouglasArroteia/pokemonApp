package com.example.pokemonapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.observables.PokemonObservables
import kotlinx.coroutines.launch

/**
 * The view model for the pokemon list
 *
 * @param repo the pokemon repository
 */
class PokemonListViewModel(
    private val repo: PokemonRepository,
    private val pokemonObservables: PokemonObservables
) : AbstractViewModel() {

    private var offset = 0

    /**
     * Gets the list of pokemons.
     */
    fun getPokemonList() {
        pokemonObservables.pokeLoadedObserver.value = false
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val data = repo.getPokemonList()
            val dataModel = handleResponse(data, ::handleError)
            dataModel?.let {
                pokemonObservables.pokeListObserver.value = it
                pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
                pokemonObservables.pokeLoadedObserver.value = true
                offset = INITIAL_POKEMON_LOADED
            }
        }
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
    }

    /**
     * Updates the list of pokemons.
     */
    fun updatePokemonList() {
        pokemonObservables.pokeLoadedObserver.value = false
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            if (offset + LAST_POKEMONS < MAX_POKEMONS) {
                val data = repo.getPokemonList(limit = POKEMON_LOADED, offset = offset)
                offset += POKEMON_LOADED
                val dataModel = handleResponse(data, ::handleError)
                dataModel?.let {
                    pokemonObservables.pokeListObserver.value = it
                    pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
                    pokemonObservables.pokeLoadedObserver.value = true
                }
            } else if (offset + LAST_POKEMONS == MAX_POKEMONS) {
                val data = repo.getPokemonList(limit = LAST_POKEMONS, offset = offset)
                offset += LAST_POKEMONS
                val dataModel = handleResponse(data, ::handleError)
                dataModel?.let {
                    pokemonObservables.pokeListObserver.value = it
                    pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
                    pokemonObservables.pokeLoadedObserver.value = true
                }
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

    companion object {

        /**
         * Initial number of pokemon request.
         */
        private const val INITIAL_POKEMON_LOADED = 10

        /**
         * Number of pokémon loaded per page (pull to refresh).
         */
        private const val POKEMON_LOADED = 40

        /**
         * Max number of pokemon found.
         */
        private const val MAX_POKEMONS = 898

        /**
         * The last 8 pokemons.
         */
        private const val LAST_POKEMONS = 8
    }
}