package com.joaquinco.androidavanzado.data

import com.joaquinco.androidavanzado.domain.SuperHero
import com.joaquinco.androidavanzado.ui.detail.DetailState

interface Repository {
    suspend fun login():LoginState
    suspend fun getHeros(): List<SuperHero>
    suspend fun getHerosWithCache(): List<SuperHero>
    suspend fun getHeroDetail(name: String): DetailState
}
