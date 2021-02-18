package com.example.pokemonapp.view.model

import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repo: PokemonRepository,
    private val pokemonModel: PokeModel
) : AbstractViewModel() {


    fun getPokemonList(limit: Int) {
        pokemonModel.pokeLoadedObserver.value = false
        pokemonModel.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val data = repo.getPokemonList(limit)
            val dataModel = handleResponse(data, ::handleError)
            dataModel?.let {
                pokemonModel.pokeListObserver.value = it
                pokemonModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
                pokemonModel.pokeLoadedObserver.value = true
            }
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