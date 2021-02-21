package com.example.pokemonapp.api.response.models

import com.google.gson.annotations.SerializedName

/**
 * Response containing the pokémon list.
 *
 * @param pokeList the pokémon list.
 */
data class PokemonListModel(
    @SerializedName("results") val pokeList: MutableList<PokemonListModelItem> =
        mutableListOf(PokemonListModelItem())
)