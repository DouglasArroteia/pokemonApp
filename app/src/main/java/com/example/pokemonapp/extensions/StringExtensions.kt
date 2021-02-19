package com.example.pokemonapp.extensions

import android.content.Context
import com.example.pokemonapp.R
import com.example.pokemonapp.api.Urls
import java.util.*

fun String.toPokemonNumber(context: Context): String =
    context.getString(R.string.pokemon_number, this)

fun String.toPokemonHeight(context: Context): String =
    context.getString(R.string.pokemon_height_dm, this)

fun String.toPokemonWeight(context: Context): String =
    context.getString(R.string.pokemon_weight_hg, this)

fun String.toPokemonStats(context: Context): String =
    context.getString(R.string.pokemon_stats, this).capitalize(Locale.US)

fun String.toPokemonType(): String =
    this.capitalize(Locale.getDefault())

fun String.pokemonId(): String = substringAfter("pokemon/").replace("/", "")


fun String.imageURL(): String = Urls.ICON_BASE_URL.plus(this.plus(".png"))

