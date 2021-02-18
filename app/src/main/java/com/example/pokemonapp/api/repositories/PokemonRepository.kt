package com.example.pokemonapp.api.repositories

import com.example.pokemonapp.api.response.PokemonDetailsResponse
import com.example.pokemonapp.api.response.PokemonListResponse
import com.example.pokemonapp.api.model.FavoriteModel


interface PokemonRepository : Repository {
    suspend fun getPokemonList(
        limit: Int
    ): Result<PokemonListResponse>

    suspend fun getPokemon(
        id: Int
    ): Result<PokemonDetailsResponse>

    suspend fun markAsFav(
        id: Int,
        body: FavoriteModel
    ): Result<Unit>
}