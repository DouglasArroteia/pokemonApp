package com.example.pokemonapp.api.response.models

import com.example.pokemonapp.api.response.models.TypeModel
import com.google.gson.annotations.SerializedName

data class TypesModel(
    @SerializedName("type") val type: TypeModel
)
