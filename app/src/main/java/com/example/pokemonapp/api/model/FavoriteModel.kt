package com.example.pokemonapp.api.model

import com.google.gson.annotations.SerializedName

data class FavoriteModel(
    @SerializedName("name") val name: String
)
