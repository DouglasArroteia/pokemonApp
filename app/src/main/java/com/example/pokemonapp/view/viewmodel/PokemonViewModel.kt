package com.example.pokemonapp.view.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.R
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import com.example.pokemonapp.view.observables.PokeModel
import kotlinx.coroutines.launch

/**
 * The view model for the pokemon info
 */
class PokemonViewModel(
    private val repo: PokemonRepository
) : AbstractViewModel() {

    val pokeModel: PokeModel = PokeModel()

    /**
     * Returns the desired pokemon.
     *
     * @param id the pokemon id.
     */
    fun getPokemon(id: Int) {
        pokeModel.pokeLoadedObserver.value = false
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(true)
        viewModelScope.launch {
            val data = repo.getPokemon(id)
            val dataModel = handleResponse(data, ::handleError)
            dataModel?.let {
                pokeModel.pokeDetailsObserver.value = it
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