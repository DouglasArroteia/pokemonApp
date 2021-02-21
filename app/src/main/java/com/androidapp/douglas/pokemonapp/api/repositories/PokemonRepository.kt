package com.androidapp.douglas.pokemonapp.api.repositories

import com.androidapp.douglas.pokemonapp.api.response.models.FavoriteModel
import com.androidapp.douglas.pokemonapp.api.response.models.PokemonDetailsModel
import com.androidapp.douglas.pokemonapp.api.response.models.PokemonListModel

/**
 * Pokémon interface repository, contains the GET and POST methods.
 * I used suspend functions here to not blocking a thread.
 */
interface PokemonRepository : Repository {

    /**
     * Returns the pokémon list.
     */
    suspend fun getPokemonList(
        limit: Int = 10,
        offset: Int = 0
    ): Result<PokemonListModel>

    /**
     * Returns the pokémon with the desired ID.
     */
    suspend fun getPokemon(
        id: Int
    ): Result<PokemonDetailsModel>

    /**
     * Sets a pokemons as favorite.
     */
    suspend fun setFavoritePokemon(
        name: String,
        body: FavoriteModel
    ): Result<Unit>
}