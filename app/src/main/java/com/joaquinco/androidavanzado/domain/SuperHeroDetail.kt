package com.joaquinco.androidavanzado.domain

data class SuperHeroDetail(
    val id: String,
    val name: String,
    val description: String,
    val photo: String,
    var favorite: Boolean
)
