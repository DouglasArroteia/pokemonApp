package com.androidapp.douglas.pokemonapp.api.response.models

import com.google.gson.annotations.SerializedName

/**
 * Model used for stats.
 *
 * @param baseStat the pokemon base stat from a given stats.
 */
data class StatsModel(
    @SerializedName("base_stat") val baseStat: Int = 0
)
