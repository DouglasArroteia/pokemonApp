package com.example.pokemonapp.view.viewmodel

import androidx.lifecycle.ViewModel

/**
 * Abstract view model.
 */
abstract class AbstractViewModel : ViewModel() {
    fun <T> handleResponse(result: Result<T>, onFailure: (e: Throwable?) -> Unit): T? {
        return result.getOrNull()
    }
}