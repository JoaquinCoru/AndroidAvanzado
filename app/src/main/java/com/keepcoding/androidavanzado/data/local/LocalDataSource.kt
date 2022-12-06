package com.keepcoding.androidavanzado.data.local

import com.keepcoding.androidavanzado.data.local.model.SuperHeroLocal

interface LocalDataSource {
    fun getHeros(): List<SuperHeroLocal>
    fun insertHeros(remoteSuperHeros: List<SuperHeroLocal>)
}
