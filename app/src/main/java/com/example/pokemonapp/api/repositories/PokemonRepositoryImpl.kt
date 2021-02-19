package com.example.pokemonapp.api.repositories

import com.example.pokemonapp.api.Error
import com.example.pokemonapp.api.RetrofitInstance
import com.example.pokemonapp.api.model.FavoriteModel
import com.example.pokemonapp.api.response.PokemonDetailsResponse
import com.example.pokemonapp.api.response.PokemonListResponse

open class PokemonRepositoryImpl : BaseRepository(), PokemonRepository {

    override suspend fun getPokemonList(limit: Int): Result<PokemonListResponse> {
        return handleResponse(errorBodyType = Error::class.java) {
            RetrofitInstance.POKE_API.getPokemonList(
                limit = limit,
            )
        }
    }

    override suspend fun getPokemon(id: Int): Result<PokemonDetailsResponse> {
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