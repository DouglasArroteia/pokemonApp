package com.example.pokemonapp.api.exception

class GenericException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)