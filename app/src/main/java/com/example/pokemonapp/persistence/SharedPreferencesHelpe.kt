package com.example.pokemonapp.persistence

import android.content.Context
import androidx.preference.PreferenceManager

class SharedPreferencesHelper(private val context: Context) {
    val sharedPrefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val editor by lazy {
        sharedPrefs.edit()
    }

    fun favorite(pokeName: String) = editor.putBoolean(pokeName, true).apply()

    fun unFavorite(pokeName: String) = editor.putBoolean(pokeName, false).apply()

    fun isFavorite(pokeName: String): Boolean = sharedPrefs.getBoolean(pokeName, false)

}