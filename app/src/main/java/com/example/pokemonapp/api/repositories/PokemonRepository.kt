package com.example.pokemonapp.api.repositories

import com.example.pokemonapp.api.response.PokemonDetailsResponse
import com.example.pokemonapp.api.response.PokemonListResponse
import com.example.pokemonapp.api.response.models.FavoriteModel

/**
 * Pokémon interface repository, contains the GET and POST methods.
 * I used suspend functions here to not blocking a thread.
 */
interface PokemonRepository : Repository {

    /**
     * Returns the pokémon list.
     */
    suspend fun getPokemonList(
        limit: Int
    ): Result<PokemonListResponse>

    /**
     * Returns the pokémon with the desired ID.
     */
    suspend fun getPokemon(
        id: Int
    ): Result<PokemonDetailsResponse>

    /**
     * Sets a pokemons as favorite.
     */
    suspend fun setFavoritePokemon(
        name: String,
        body: FavoriteModel
    ): Result<Unit>
}