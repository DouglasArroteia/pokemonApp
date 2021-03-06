package com.androidapp.douglas.pokemonapp.api.repositories

import com.androidapp.douglas.pokemonapp.api.exception.GenericException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Used as a base for creating the pokemon Data repository.
 */
abstract class BaseRepository {

    /**
     * Handles the response from the JSON.
     */
    protected suspend fun <T, R> handleResponse(
        errorBodyType: Class<R>? = null,
        call: suspend () -> Response<T>
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val parsedBody = response.body() ?: throw NullPointerException()
                    Result.success(parsedBody)
                } else {
                    val errorBody = try {
                        Gson().fromJson(response.errorBody()?.string(), errorBodyType)
                    } catch (e: JsonSyntaxException) {
                        null
                    }
                    when (response.code()) {
                        else -> {
                            throw GenericException(errorBody = errorBody)
                        }
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
