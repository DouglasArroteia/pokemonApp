package com.androidapp.douglas.pokemonapp.api.exception

/**
 * Generic exception class.
 */
class GenericException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)