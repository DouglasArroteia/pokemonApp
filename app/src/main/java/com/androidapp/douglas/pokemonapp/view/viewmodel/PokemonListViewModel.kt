package com.androidapp.douglas.pokemonapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.androidapp.douglas.pokemonapp.api.repositories.PokemonRepository
import com.androidapp.douglas.pokemonapp.loader.PokemonLoader
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables
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
        pokemonObservables.apply {
            pokeLoadedObserver.value = false
            pokeLoaderObserver.value = PokemonLoader.Loading(true)
            viewModelScope.launch {
                val data = repo.getPokemonList()
                val dataModel = handleResponse(data, this@apply)
                dataModel?.let {
                    pokeListObserver.value = it
                    pokeLoaderObserver.value = PokemonLoader.Loading(false)
                    pokeLoadedObserver.value = true
                    offset = INITIAL_POKEMON_LOADED
                }
            }
            pokeLoaderObserver.value = PokemonLoader.Loading(false)
        }
    }

    /**
     * Updates the list of pokemons.
     */
    fun updatePokemonList() {
        pokemonObservables.apply {
            pokeLoadedObserver.value = false
            pokeLoaderObserver.value = PokemonLoader.Loading(true)
            viewModelScope.launch {
                if (offset + LAST_POKEMONS < MAX_POKEMONS) {
                    val data = repo.getPokemonList(limit = POKEMON_LOADED, offset = offset)
                    offset += POKEMON_LOADED
                    val dataModel = handleResponse(data, this@apply)
                    dataModel?.let {
                        pokeListObserver.value = it
                        pokeLoaderObserver.value = PokemonLoader.Loading(false)
                        pokeLoadedObserver.value = true
                    }
                } else if (offset + LAST_POKEMONS == MAX_POKEMONS) {
                    val data = repo.getPokemonList(limit = LAST_POKEMONS, offset = offset)
                    offset += LAST_POKEMONS
                    val dataModel = handleResponse(data, this@apply)
                    dataModel?.let {
                        pokeListObserver.value = it
                        pokeLoaderObserver.value = PokemonLoader.Loading(false)
                        pokeLoadedObserver.value = true
                    }
                }
            }
            pokeLoaderObserver.value = PokemonLoader.Loading(false)
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