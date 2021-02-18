package com.example.pokemonapp.api

import com.google.gson.annotations.SerializedName
import okhttp3.internal.http2.ErrorCode

data class Error(
    @SerializedName("cod") val code: ErrorCode?,
    @SerializedName("message") val detail: String?
)