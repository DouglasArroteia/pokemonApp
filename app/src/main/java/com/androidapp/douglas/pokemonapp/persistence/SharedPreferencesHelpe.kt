package com.example.pokemonapp.persistence

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * For an easy communication with the SharedPreferences.
 */
class SharedPreferencesHelper(private val context: Context) {
    private val sharedPrefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val editor by lazy {
        sharedPrefs.edit()
    }

    /**
     * Favorites the desired pokémon.
     *
     * @param pokeName the pokemon name to be favorited
     */
    fun favorite(pokeName: String) = editor.putBoolean(pokeName, true).apply()

    /**
     * Unfavorites the desired pokémon.
     *
     * @param pokeName the pokemon name to be unfavorited
     */
    fun unFavorite(pokeName: String) = editor.putBoolean(pokeName, false).apply()

    /**
     * Checks if the desired pokemon is favorited
     *
     * @param pokeName the pokemon name to be checked
     * @return true if it was favorited, otherwise false
     */
    fun isFavorite(pokeName: String): Boolean = sharedPrefs.getBoolean(pokeName, false)

}