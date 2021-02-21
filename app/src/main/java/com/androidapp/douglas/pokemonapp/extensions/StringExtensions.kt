package com.androidapp.douglas.pokemonapp.extensions

import android.content.Context
import com.androidapp.douglas.pokemonapp.api.Urls
import com.example.pokemonapp.R
import java.util.*

/**
 * String extension for transforming a desired String into a pokemon number.
 *
 * Example: "# 65"
 */
fun String.toPokemonNumber(context: Context): String =
    context.getString(R.string.pokemon_number, this)

/**
 * String extension for transforming a desired String into a pokemon height in decimeters.
 *
 * Example: "50 dm"
 */
fun String.toPokemonHeight(context: Context): String =
    context.getString(R.string.pokemon_height_dm, this)

/**
 * String extension for transforming a desired String into a pokemon weight in hectograms.
 *
 * Example: "100 hg"
 */
fun String.toPokemonWeight(context: Context): String =
    context.getString(R.string.pokemon_weight_hg, this)

/**
 * String extension for transforming a desired String into a stats info title, with the first
 * letters capitalised
 *
 * Example: "Charmandes stats"
 */
fun String.toPokemonStats(context: Context): String =
    context.getString(R.string.pokemon_stats, this).capitalize(Locale.US)

/**
 * String extension for transforming a desired String into a pokemon type, with the first
 * letter capitalized.
 *
 * Example: "Grass"
 */
fun String.toPokemonType(): String =
    this.capitalize(Locale.getDefault())

/**
 * String extension for transforming a desired String into a pokemon id.
 *
 * Example: "65"
 */
fun String.pokemonId(): String = substringAfter("pokemon/").replace("/", "")


/**
 * String extension for transforming a desired String into a pokemon number.
 *
 * Example: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"
 */
fun String.imageURL(): String = Urls.ICON_BASE_URL.plus(this.plus(".png"))

