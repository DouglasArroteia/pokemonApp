package com.androidapp.douglas.pokemonapp.api

import com.google.gson.annotations.SerializedName
import okhttp3.internal.http2.ErrorCode

/**
 * Error model.
 *
 *  @param code the code of the error
 *  @param detail the detailed message of the error
 */
data class Error(
    @SerializedName("cod") val code: ErrorCode?,
    @SerializedName("message") val detail: String?
)