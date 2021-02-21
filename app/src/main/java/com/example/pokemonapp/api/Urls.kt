package com.example.pokemonapp.api

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
        const val BASE_URL_POST = "https://webhook.site/9b51676e-2571-4d5b-af09-1ff1b4f68b66"

        /*
        Url used to get the pokémon sprites, used as icon
         */
        const val ICON_BASE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    }
}