package com.keepcoding.androidavanzado.data

import com.keepcoding.androidavanzado.domain.SuperHero

interface Repository {
    suspend fun login():LoginState
    suspend fun getHeros(): List<SuperHero>
    suspend fun getHerosWithCache(): List<SuperHero>
}
