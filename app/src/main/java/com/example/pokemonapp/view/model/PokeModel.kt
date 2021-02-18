package com.example.pokemonapp.view.model

import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.api.response.PokemonDetailsResponse
import com.example.pokemonapp.api.response.PokemonListResponse
import com.example.pokemonapp.loader.PokemonLoader

/**
 * Model used to observe all pokemon features.
 */
class PokeModel {

    /**
     * Observer for the pokemon list response from api.
     */
    val pokeListObserver = MutableLiveData<PokemonListResponse>()

    /**
     * Observer for the pokemon details response from api.
     */
    val pokeDetailsObserver = MutableLiveData<PokemonDetailsResponse>()

    /**
     * Observer for the pokemon id response from api.
     */
    val pokeIdObserver = MutableLiveData<Int>()

    /**
     * Observer for the pokemon loader response from api.
     */
    val pokeLoaderObserver = MutableLiveData<PokemonLoader>()

    /**
     * Observer for the pokemon loaded response from api.
     */
    val pokeLoadedObserver = MutableLiveData<Boolean>().apply { value = false }
}