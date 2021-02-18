package com.example.pokemonapp.api

import com.example.pokemonapp.api.Urls.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val POKE_API: Pokemons by lazy {
        retrofit.create(Pokemons::class.java)
    }

    val FAV_API: Favorites by lazy {
        retrofit.create(Favorites::class.java)
    }
}