package com.example.pokemonapp.extensions

import android.content.Context
import com.example.pokemonapp.R
import com.example.pokemonapp.api.Urls

fun String.toPokemonNumber(context: Context): String {
    return context.getString(R.string.pokemon_number, this)
}

fun String.imageURL(): String? {
    return Urls.ICON_BASE_URL.plus(this.plus(".png"))
}