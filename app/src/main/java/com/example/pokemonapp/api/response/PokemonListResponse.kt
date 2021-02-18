package com.example.pokemonapp.api.response

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("results") val result: MutableList<PokemonListResponseItem>
)