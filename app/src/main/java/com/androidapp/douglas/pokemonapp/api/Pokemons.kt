package com.androidapp.douglas.pokemonapp.api

import com.androidapp.douglas.pokemonapp.api.response.models.PokemonDetailsModel
import com.androidapp.douglas.pokemonapp.api.response.models.PokemonListModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface that handle pokemon GET methods.
 */
interface Pokemons {

    /**
     * Gets the pokémon list
     * @param limit the limit of pokémons returned.
     */
    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int,
    ): Response<PokemonListModel>

    /**
     * Gets a specific pokémon.
     * @param id the id of the desired pokemon info
     */
    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): Response<PokemonDetailsModel>
}