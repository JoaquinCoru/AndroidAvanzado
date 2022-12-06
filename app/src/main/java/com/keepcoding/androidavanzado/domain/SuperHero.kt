package com.keepcoding.androidavanzado.domain

data class SuperHero(
    val id: String,
    val name: String,
    val photo: String
)

//fun SuperHero.toLocal(): SuperHeroLocal {
//    return SuperHeroLocal(this.id, this.name, this.photo, "", false)
//}
