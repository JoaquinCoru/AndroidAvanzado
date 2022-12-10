package com.keepcoding.androidavanzado.data

import com.keepcoding.androidavanzado.domain.Bootcamp
import com.keepcoding.androidavanzado.domain.SuperHero

interface Repository {
    suspend fun login():String
    suspend fun getBootcamps(): List<Bootcamp>
    suspend fun getHeros(): List<SuperHero>
    suspend fun getHerosWithCache(): List<SuperHero>
}
