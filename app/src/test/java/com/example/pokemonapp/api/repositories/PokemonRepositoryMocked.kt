package com.example.pokemonapp.api.repositories

import com.example.pokemonapp.api.exception.GenericException
import com.example.pokemonapp.api.response.models.PokemonDetailsModel
import com.example.pokemonapp.api.response.models.PokemonListModel
import com.example.pokemonapp.api.response.models.PokemonListModelItem
import com.example.pokemonapp.api.response.models.FavoriteModel
import com.example.pokemonapp.api.response.models.StatsModel
import com.example.pokemonapp.api.response.models.TypeModel
import com.example.pokemonapp.api.response.models.TypesModel

/**
 * Fake repository with pokemon data.
 */
class PokemonRepositoryMocked : PokemonRepository {
    override suspend fun getPokemonList(limit: Int): Result<PokemonListModel> {
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