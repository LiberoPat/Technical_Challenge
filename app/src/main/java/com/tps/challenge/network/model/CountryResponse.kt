package com.tps.challenge.network.model

import com.google.gson.annotations.SerializedName

/**
 * Store remote data model.
 */
data class CountryResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("capital")
    val capital: String
)
