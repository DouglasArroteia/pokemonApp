package com.androidapp.douglas.pokemonapp.extensions

import android.content.res.Configuration
import android.content.res.Resources

/**
 * Resource extension to check if device is on landscape mode.
 *
 * @return true if on landscape, otherwise false
 */
fun Resources.isLandscape(): Boolean =
    this.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


