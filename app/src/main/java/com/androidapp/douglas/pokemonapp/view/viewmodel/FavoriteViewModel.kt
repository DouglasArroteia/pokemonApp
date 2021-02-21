package com.androidapp.douglas.pokemonapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.androidapp.douglas.pokemonapp.api.repositories.PokemonRepository
import com.androidapp.douglas.pokemonapp.api.response.models.FavoriteModel
import com.androidapp.douglas.pokemonapp.loader.PokemonLoader
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables
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
        pokemonObservables.apply {
            pokeLoaderObserver.value = PokemonLoader.Loading(true)
            viewModelScope.launch {
                val model = FavoriteModel(name)
                val response = repo.setFavoritePokemon(name, model)
                handleResponse(response, pokemonObservables)
            }
            pokeLoaderObserver.value = PokemonLoader.Loading(false)
        }
    }
}