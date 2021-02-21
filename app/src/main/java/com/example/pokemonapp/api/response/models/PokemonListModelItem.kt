package com.example.pokemonapp.api.response.models

import com.google.gson.annotations.SerializedName

/**
 * Response containing the desired name and url.
 *
 * @param name the pokémon name.
 * @param url the pokémon url.
 */
data class PokemonListModelItem(
    @SerializedName("name") val name: String = "",
    @SerializedName("url") val url: String = ""
)
