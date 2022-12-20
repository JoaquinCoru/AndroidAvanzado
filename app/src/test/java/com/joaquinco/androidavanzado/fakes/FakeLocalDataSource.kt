package com.joaquinco.androidavanzado.fakes

import com.joaquinco.androidavanzado.data.local.LocalDataSource
import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal
import com.joaquinco.androidavanzado.utils.generateHerosLocal


class FakeLocalDataSource: LocalDataSource {
    private var firstCall = true

    override fun getHeros(): List<SuperHeroLocal> {
        return if (firstCall){
            firstCall = false
            emptyList()
        } else {
            generateHerosLocal()
        }
    }

    override fun insertHeros(remoteSuperHeros: List<SuperHeroLocal>) {

    }

    override fun updateHero(localHero: SuperHeroLocal) {

    }
}
