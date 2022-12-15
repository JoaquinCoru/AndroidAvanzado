package com.joaquinco.androidavanzado.data

import com.joaquinco.androidavanzado.domain.SuperHero

interface Repository {
    suspend fun login():LoginState
    suspend fun getHeros(): List<SuperHero>
    suspend fun getHerosWithCache(): List<SuperHero>
}
