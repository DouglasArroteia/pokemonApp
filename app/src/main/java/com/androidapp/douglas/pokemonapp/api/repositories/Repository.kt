package com.example.pokemonapp.api.repositories

/**
 * The repository.
 */
interface Repository {

    /**
     * Handles the result.
     */
    fun <T> handleResult(
        result: Result<T>,
        onFailure: (e: Throwable?) -> Unit = {}
    ): T? {
        return if (result.isFailure) {
            onFailure(result.exceptionOrNull())
            null
        } else {
            result.getOrNull()
        }
    }
}