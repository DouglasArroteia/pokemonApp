package com.androidapp.douglas.pokemonapp.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.androidapp.douglas.pokemonapp.api.repositories.PokemonRepository
import com.androidapp.douglas.pokemonapp.loader.PokemonLoader
import com.androidapp.douglas.pokemonapp.view.observables.PokemonObservables
import kotlinx.coroutines.launch

/**
 * The view model for the pokemon info
 */
class PokemonViewModel(
    private val repo: PokemonRepository,
    private val pokemonObservables: PokemonObservables
) : AbstractViewModel() {

    /**
     * Returns the desired pokemon.
     *
     * @param id the pokemon id.
     */
    fun getPokemon(id: Int) {
        pokemonObservables.apply {
            pokeLoadedObserver.value = false
            pokeLoaderObserver.value = PokemonLoader.Loading(true)
            viewModelScope.launch {
                val data = repo.getPokemon(id)
                val dataModel = handleResponse(data, this@apply)
                dataModel?.let {
                    pokeDetailsObserver.value = it
                    pokeLoaderObserver.value = PokemonLoader.Loading(false)
                    pokeLoadedObserver.value = true
                }
            }
            pokeLoaderObserver.value = PokemonLoader.Loading(false)
        }
    }
}