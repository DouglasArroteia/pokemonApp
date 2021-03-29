package com.androidapp.douglas.pokemonapp.api.repositories

import com.androidapp.douglas.pokemonapp.api.Error
import com.androidapp.douglas.pokemonapp.api.RetrofitInstance
import com.androidapp.douglas.pokemonapp.api.response.models.FavoriteModel
import com.androidapp.douglas.pokemonapp.api.response.models.PokemonDetailsModel
import com.androidapp.douglas.pokemonapp.api.response.models.PokemonListModel

/**
 * Contains the implementation of the Retrofit functions.
 */
open class PokemonRepositoryImpl : BaseRepository(), PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Result<PokemonListModel> {
        return handleResponse(errorBodyType = Error::class.java) {
            RetrofitInstance.POKE_API.getPokemonList(
                limit = limit,
                offset = offset
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