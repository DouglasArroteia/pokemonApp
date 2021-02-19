package com.example.pokemonapp.view.model

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.model.FavoriteModel
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repo: PokemonRepository
) : AbstractViewModel() {

    private val pokeModel: PokeModel = PokeModel()

    fun setFavorite(name: String) {
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val model = FavoriteModel(name)
            val response = repo.setFavoritePokemon(name, model)
            handleResponse(response, ::handleError)
        }
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
    }

    private fun handleError(error: Throwable?) {
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokeModel.pokeLoaderObserver.value = PokemonLoader.GenericError(it)
        }
    }
}