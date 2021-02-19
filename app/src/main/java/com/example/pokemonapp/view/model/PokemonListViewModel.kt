package com.example.pokemonapp.view.model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repo: PokemonRepository
) : AbstractViewModel() {

    val pokeModel: PokeModel = PokeModel()

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

    private fun handleError(error: Throwable?) {
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokeModel.pokeLoaderObserver.value = PokemonLoader.GenericError(it)
        }
    }
}