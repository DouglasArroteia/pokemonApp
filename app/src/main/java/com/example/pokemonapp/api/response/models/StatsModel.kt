package com.example.pokemonapp.api.response.models

import com.google.gson.annotations.SerializedName

data class StatsModel(
    @SerializedName("base_stat") val baseStat: Float
)
