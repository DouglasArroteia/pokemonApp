package com.example.pokemonapp.api.repositories

import com.example.pokemonapp.api.exception.GenericException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {

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
