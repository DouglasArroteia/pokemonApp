package com.example.pokemonapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.api.response.models.FavoriteModel
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.observables.PokemonObservables
import kotlinx.coroutines.launch

/**
 * View model for the favorite use-case.
 *
 * @param repo the pokemon repository
 */
class FavoriteViewModel(
    private val repo: PokemonRepository,
    private val pokemonObservables: PokemonObservables
) : AbstractViewModel() {

    /**
     * Sets the pokemon as favorite.
     *
     * @param name the pokemon name to be favorited
     */
    fun setFavorite(name: String) {
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val model = FavoriteModel(name)
            val response = repo.setFavoritePokemon(name, model)
            handleResponse(response, ::handleError)
        }
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
    }

    /**
     * Handles the error
     *
     * @param error the throwable error.
     */
    private fun handleError(error: Throwable?) {
        pokemonObservables.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokemonObservables.pokeLoaderObserver.value = PokemonLoader.DefaultError(it)
        }
    }
}