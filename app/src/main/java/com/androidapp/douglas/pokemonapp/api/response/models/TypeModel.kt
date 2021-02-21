package com.example.pokemonapp.api.response.models

import com.google.gson.annotations.SerializedName

/**
 * Model used for type.
 *
 * @param name the pokemon type (s).
 */
data class TypeModel(
    @SerializedName("name") val name: String = ""
)
