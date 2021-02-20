package com.example.pokemonapp.api.repositories

import com.example.pokemonapp.api.exception.GenericException
import com.example.pokemonapp.api.response.PokemonDetailsResponse
import com.example.pokemonapp.api.response.PokemonListResponse
import com.example.pokemonapp.api.response.PokemonListResponseItem
import com.example.pokemonapp.api.response.models.FavoriteModel
import com.example.pokemonapp.api.response.models.StatsModel
import com.example.pokemonapp.api.response.models.TypeModel
import com.example.pokemonapp.api.response.models.TypesModel

/**
 * Fake repository with pokemon data.
 */
class PokemonRepositoryMocked : PokemonRepository {
    override suspend fun getPokemonList(limit: Int): Result<PokemonListResponse> {
        return Result.success(
            PokemonListResponse(
                mutableListOf(
                    PokemonListResponseItem(
                        name = "list_test",
                        url = "url_test"
                    )
                )
            )
        )
    }

    fun getPokemonListFails(): Result<PokemonListResponse> {
        return Result.failure(GenericException("default error on list"))
    }

    override suspend fun getPokemon(id: Int): Result<PokemonDetailsResponse> {
        return Result.success(
            PokemonDetailsResponse(
                height = 120,
                id = 28,
                name = "Toothless",
                types = mutableListOf(
                    TypesModel(TypeModel("dragon"))
                ),
                weight = 1200,
                stats = mutableListOf(
                    StatsModel(baseStat = 200f),
                    StatsModel(baseStat = 143f),
                    StatsModel(baseStat = 23f),
                    StatsModel(baseStat = 65f),
                    StatsModel(baseStat = 156f),
                    StatsModel(baseStat = 255f),
                )
            )
        )
    }

    fun getPokemonFails(): Result<PokemonListResponse> {
        return Result.failure(GenericException("default error on retrieving pokemon info"))
    }

    override suspend fun setFavoritePokemon(name: String, body: FavoriteModel): Result<Unit> {
        return Result.success(Unit)
    }

    fun setFavoritePokemonFails(): Result<Unit> {
        return Result.failure(GenericException("default error on post"))
    }
}