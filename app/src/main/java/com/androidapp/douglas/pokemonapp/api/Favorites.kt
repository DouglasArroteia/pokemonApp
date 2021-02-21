package com.androidapp.douglas.pokemonapp.api

import com.androidapp.douglas.pokemonapp.api.response.models.FavoriteModel
import com.androidapp.douglas.pokemonapp.api.Urls
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

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