package com.example.pokemonapp.api

import com.example.pokemonapp.api.response.models.FavoriteModel
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface that handle POST favorite method.
 */
interface Favorites {

    /**
     * POSTS the favorite to a webhook site.
     */
    @POST(Urls.BASE_URL_POST)
    suspend fun setFavoritePokemon(
        @Query("name") name: String,
        @Body body: FavoriteModel
    ): Response<Unit>
}