package com.example.pokemonapp.view.model

import androidx.lifecycle.ViewModel
import com.example.pokemonapp.loader.PokemonLoader

abstract class AbstractViewModel : ViewModel() {
    fun <T> handleResponse(result: Result<T>, onFailure: (e: Throwable?) -> Unit): T? {
        return result.getOrNull()
    }
}