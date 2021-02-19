package com.example.pokemonapp.api.response

import com.google.gson.annotations.SerializedName

/**
 * Response containing the pokémon list.
 *
 * @param list the pokémon list.
 */
data class PokemonListResponse(
    @SerializedName("results") val list: MutableList<PokemonListResponseItem>
)