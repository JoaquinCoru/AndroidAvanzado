package com.joaquinco.androidavanzado.domain

import com.joaquinco.androidavanzado.data.LoginState
import com.joaquinco.androidavanzado.ui.detail.DetailState
import com.joaquinco.androidavanzado.ui.detail.LikeState

interface Repository {
    suspend fun login(): LoginState
    suspend fun getHeros(): List<SuperHero>
    suspend fun getHerosWithCache(): List<SuperHero>
    suspend fun getHeroDetail(name: String): DetailState
    suspend fun getLocations(id: String): List<Location>
    suspend fun setLike(superHeroDetail: SuperHeroDetail): LikeState
    suspend fun getFavorites():List<SuperHero>
}
