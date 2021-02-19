package com.example.pokemonapp.view.model

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.R
import com.example.pokemonapp.api.repositories.PokemonRepository
import com.example.pokemonapp.loader.PokemonLoader
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repo: PokemonRepository,
    private val context: Context
) : AbstractViewModel() {

    val pokeModel: PokeModel = PokeModel()

    val statsColor = arrayOf<Any>(
        context.getString(R.string.status_attack),
        context.getString(R.string.status_s_attack),
        context.getString(R.string.status_hp),
        context.getString(R.string.status_def),
        context.getString(R.string.status_s_def),
        context.getString(R.string.status_speed)
    )

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

    private fun handleError(error: Throwable?) {
        pokeModel.pokeLoaderObserver.value = PokemonLoader.Loading(false)
        error?.message?.let {
            pokeModel.pokeLoaderObserver.value = PokemonLoader.GenericError(it)
        }
    }
}