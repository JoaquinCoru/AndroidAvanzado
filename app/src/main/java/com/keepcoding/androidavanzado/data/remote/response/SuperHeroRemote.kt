package com.keepcoding.androidavanzado.data.remote.response

import com.squareup.moshi.Json

data class SuperHeroRemote(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "photo") val photo: String,
    @Json(name = "description") val description: String,
)
