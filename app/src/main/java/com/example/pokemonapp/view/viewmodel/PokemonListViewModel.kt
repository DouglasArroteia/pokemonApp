package com.example.pokemonapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.observables.PokeModel
import kotlinx.coroutines.launch

/**
 * The view model for the pokemon list
 *
 * @param repo the pokemon repository
 */
class PokemonListViewModel(
    private val repo: PokemonRepository
) : AbstractViewModel() {

    val pokeModel: PokeModel = PokeModel()

    /**
     * Gets the list of pokemons.
     *
     * @param limit the limit of pokemons returned.
     */
    fun getPokemonList(limit: Int) {
        pokeModel.pokeLoadedObserver.value = false
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val data = repo.getPokemonList(limit)
            val dataModel = handleResponse(data, ::handleError)
            dataModel?.let {
                pokeModel.pokeListObserver.value = it
                pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
                pokeModel.pokeLoadedObserver.value = true
            }
        }
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
    }

    /**
     * Handles any error.
     */
    private fun handleError(error: Throwable?) {
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokeModel.pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
        }
    }
}