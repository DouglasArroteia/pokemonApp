package com.example.pokemonapp.view.model

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.api.response.models.FavoriteModel
import com.example.pokemonapp.loader.PokemonLoader
import kotlinx.coroutines.launch

/**
 * View model for the favorite use-case.
 *
 * @param repo the pokemon repository
 */
class FavoriteViewModel(
    private val repo: PokemonRepository
) : AbstractViewModel() {

    private val pokeModel: PokeModel = PokeModel()

    /**
     * Sets the pokemon as favorite.
     *
     * @param name the pokemon name to be favorited
     */
    fun setFavorite(name: String) {
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val model = FavoriteModel(name)
            val response = repo.setFavoritePokemon(name, model)
            handleResponse(response, ::handleError)
        }
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
    }

    /**
     * Handles the error
     *
     * @param error the throwable error.
     */
    private fun handleError(error: Throwable?) {
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokeModel.pokeLoaderObserver.value = PokemonLoader.GenericError(it)
        }
    }
}