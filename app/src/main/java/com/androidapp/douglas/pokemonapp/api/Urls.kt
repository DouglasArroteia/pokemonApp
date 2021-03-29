package com.androidapp.douglas.pokemonapp.api

/**
 * URLS used on this project
 */
internal class Urls {
    companion object {
        /*
        Base API URL
         */
        const val BASE_URL = "https://pokeapi.co/api/v2/"

        /*
        Webhook url used for POST
         */
        const val BASE_URL_POST = "https://webhook.site/7df7d85f-5eb0-4261-b82e-9d86ec8063fc"

        /*
        Url used to get the pokémon sprites, used as icon
         */
        const val ICON_BASE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    }
}