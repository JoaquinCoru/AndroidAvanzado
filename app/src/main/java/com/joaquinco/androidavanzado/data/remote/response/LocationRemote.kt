package com.joaquinco.androidavanzado.data.remote.response

import com.squareup.moshi.Json

data class LocationRemote(
    @Json(name = "id") val id: String,
    @Json(name = "longitud") val longitud: String,
    @Json(name = "latitud") val latitud: String
)