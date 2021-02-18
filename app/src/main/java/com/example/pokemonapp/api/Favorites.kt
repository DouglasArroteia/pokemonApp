package com.example.pokemonapp.api

import com.example.pokemonapp.api.model.FavoriteModel
import retrofit2.Response
import retrofit2.http.*

interface Favorites {

    @POST(Urls.BASE_URL_POST)
    suspend fun markAsFav(
        @Query("id") id: Int,
        @Body body: FavoriteModel
    ): Response<Unit>
}