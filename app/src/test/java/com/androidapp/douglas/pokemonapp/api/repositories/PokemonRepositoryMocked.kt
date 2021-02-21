package com.androidapp.douglas.pokemonapp.api.repositories

import com.androidapp.douglas.pokemonapp.api.exception.GenericException
import com.androidapp.douglas.pokemonapp.api.response.models.*

/**
 * Fake repository with pokemon data.
 */
class PokemonRepositoryMocked : PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Result<PokemonListModel> {
        return Result.success(
            PokemonListModel(
                mutableListOf(
                    PokemonListModelItem(
                        name = "list_test",
                        url = "url_test"
                    )
                )
            )
        )
    }

    fun getPokemonListFails(): Result<PokemonListModel> {
        return Result.failure(GenericException("default error on list"))
    }

    override suspend fun getPokemon(id: Int): Result<PokemonDetailsModel> {
        return Result.success(
            PokemonDetailsModel(
                height = 120,
                id = 28,
                name = "Toothless",
                types = mutableListOf(
                    TypesModel(TypeModel("dragon"))
                ),
                weight = 1200,
                stats = mutableListOf(
                    StatsModel(baseStat = 200),
                    StatsModel(baseStat = 143),
                    StatsModel(baseStat = 23),
                    StatsModel(baseStat = 65),
                    StatsModel(baseStat = 156),
                    StatsModel(baseStat = 250),
                )
            )
        )
    }

    fun getPokemonFails(): Result<PokemonListModel> {
        return Result.failure(GenericException("default error on retrieving pokemon info"))
    }

    override suspend fun setFavoritePokemon(name: String, body: FavoriteModel): Result<Unit> {
        return Result.success(Unit)
    }

    fun setFavoritePokemonFails(): Result<Unit> {
        return Result.failure(GenericException("default error on post"))
    }
}