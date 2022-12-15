package com.joaquinco.androidavanzado.data.local

import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal

interface LocalDataSource {
    fun getHeros(): List<SuperHeroLocal>
    fun insertHeros(remoteSuperHeros: List<SuperHeroLocal>)
}
