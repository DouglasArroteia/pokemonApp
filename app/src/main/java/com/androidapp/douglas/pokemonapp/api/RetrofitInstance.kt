package com.androidapp.douglas.pokemonapp.api

import com.androidapp.douglas.pokemonapp.api.Urls.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Instance of retrofit.
 */
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Used to creat the Retrofit class for Pokemon
     */
    val POKE_API: Pokemons by lazy {
        retrofit.create(Pokemons::class.java)
    }

    /**
     * Used to creat the Retrofit class for Favorites
     */
    val FAV_API: Favorites by lazy {
        retrofit.create(Favorites::class.java)
    }
}