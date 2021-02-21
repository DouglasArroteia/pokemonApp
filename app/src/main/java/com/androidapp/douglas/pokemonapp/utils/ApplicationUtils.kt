package com.androidapp.douglas.pokemonapp.utils

import android.content.Context
import com.example.pokemonapp.R

class ApplicationUtils(context: Context) {

    /**
     * Colors used for shown each pokemon stats
     */
    val statsColor = arrayOf<Any>(
        context.getString(R.string.status_attack),
        context.getString(R.string.status_s_attack),
        context.getString(R.string.status_hp),
        context.getString(R.string.status_def),
        context.getString(R.string.status_s_def),
        context.getString(R.string.status_speed)
    )
}