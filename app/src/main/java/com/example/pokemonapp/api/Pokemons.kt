package com.example.pokemonapp.api

import com.example.pokemonapp.api.response.PokemonDetailsResponse
import com.example.pokemonapp.api.response.PokemonListResponse
import retrofit2.Response
import retrofit2.http.*

interface Pokemons {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): Response<PokemonDetailsResponse>
}