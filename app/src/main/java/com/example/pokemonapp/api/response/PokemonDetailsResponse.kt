package com.example.pokemonapp.api.response

import com.example.pokemonapp.api.response.models.StatsModel
import com.example.pokemonapp.api.response.models.TypesModel
import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(
    @SerializedName("height") val height: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("types") val types: MutableList<TypesModel>,
    @SerializedName("weight") val weight: Int,
    @SerializedName("stats") val stats: MutableList<StatsModel>
)
