package com.androidapp.douglas.pokemonapp.utils

import android.annotation.SuppressLint
import android.content.Context
import com.androidapp.douglas.pokemonapp.R
import com.androidapp.douglas.pokemonapp.extensions.noAlphaColor

class ApplicationUtils(context: Context) {

    /**
     * Colors used for shown each pokemon stats
     */
    @SuppressLint("ResourceType")
    val statsColor = arrayOf<Any>(
        context.getString(R.color.status_attack).noAlphaColor(),
        context.getString(R.color.status_s_attack).noAlphaColor(),
        context.getString(R.color.status_hp).noAlphaColor(),
        context.getString(R.color.status_def).noAlphaColor(),
        context.getString(R.color.status_s_def).noAlphaColor(),
        context.getString(R.color.status_speed).noAlphaColor()
    )
}