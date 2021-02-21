package com.example.pokemonapp.api.response.models

import com.google.gson.annotations.SerializedName

/**
 * Model used for favorites.
 *
 * @param name the pokemon name for being favorited.
 */
data class FavoriteModel(
    @SerializedName("name") val name: String = ""
)
