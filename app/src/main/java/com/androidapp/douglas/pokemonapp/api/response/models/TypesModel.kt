package com.androidapp.douglas.pokemonapp.api.response.models

import com.androidapp.douglas.pokemonapp.api.response.models.TypeModel
import com.google.gson.annotations.SerializedName

/**
 * Model used for favorites.
 *
 * @param type the pokemon type
 */
data class TypesModel(
    @SerializedName("type") val type: TypeModel = TypeModel()
)
