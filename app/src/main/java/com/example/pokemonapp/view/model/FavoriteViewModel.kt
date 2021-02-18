package com.example.pokemonapp.view.model

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.model.FavoriteModel
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repo: PokemonRepository,
    private val pokemonModel: PokeModel
) : AbstractViewModel() {

    fun setFavorite(id: Int) {
        pokemonModel.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val model = FavoriteModel(id)
            val response = repo.markAsFav(id, model)
            handleResponse(response, ::handleError)
        }
        pokemonModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
    }

    private fun handleError(error: Throwable?) {
        pokemonModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokemonModel.pokeLoaderObserver.value = PokemonLoader.GenericError(it)
        }
    }
}