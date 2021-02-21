package com.example.pokemonapp.api.response.models

import com.google.gson.annotations.SerializedName

/**
 * Response containing the desired pokémon details.
 *
 * @param height the pokemon height.
 * @param id the pokemon id
 * @param name the pokemon name
 * @param types the pokemon types
 * @param weight the pokemon weight
 * @param stats the pokemon stats
 */
data class PokemonDetailsModel(
    @SerializedName("height") val height: Int = 0,
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("types") val types: MutableList<TypesModel> = mutableListOf(TypesModel()),
    @SerializedName("weight") val weight: Int = 0,
    @SerializedName("stats") val stats: MutableList<StatsModel> = mutableListOf(StatsModel())
)
