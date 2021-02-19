package com.example.pokemonapp.view.model

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.R
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import kotlinx.coroutines.launch

/**
 * The view model for the pokemon info
 */
class PokemonViewModel(
    private val repo: PokemonRepository,
    private val context: Context
) : AbstractViewModel() {

    val pokeModel: PokeModel = PokeModel()

    /**
     * Colors used for shown each pokemon stats
     */
    val statsColor = arrayOf<Any>(
        context.getString(R.string.status_attack),
        context.getString(R.string.status_s_attack),
        context.getString(R.string.status_hp),
        context.getString(R.string.status_def),
        context.getString(R.string.status_s_def),
        context.getString(R.string.status_speed)
    )

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
            pokeModel.pokeLoaderObserver.value = PokemonLoader.GenericError(it)
        }
    }
}