package com.example.pokemonapp.api.repositories

import com.example.pokemonapp.api.Error
import com.example.pokemonapp.api.RetrofitInstance
import com.example.pokemonapp.api.response.models.PokemonDetailsModel
import com.example.pokemonapp.api.response.models.PokemonListModel
import com.example.pokemonapp.api.response.models.FavoriteModel

/**
 * Contains the implementation of the Retrofit functions.
 */
open class PokemonRepositoryImpl : BaseRepository(), PokemonRepository {

    override suspend fun getPokemonList(limit: Int): Result<PokemonListModel> {
        return handleResponse(errorBodyType = Error::class.java) {
            RetrofitInstance.POKE_API.getPokemonList(
                limit = limit,
            )
        }
    }

    override suspend fun getPokemon(id: Int): Result<PokemonDetailsModel> {
        return handleResponse(errorBodyType = Error::class.java) {
            RetrofitInstance.POKE_API.getPokemon(
                id = id
            )
        }
    }

    override suspend fun setFavoritePokemon(name: String, body: FavoriteModel): Result<Unit> {
        return handleResponse(errorBodyType = Error::class.java) {
            RetrofitInstance.FAV_API.setFavoritePokemon(name, body)
        }
    }
}