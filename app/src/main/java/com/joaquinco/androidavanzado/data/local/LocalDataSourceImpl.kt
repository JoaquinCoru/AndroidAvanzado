package com.joaquinco.androidavanzado.data.local

import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dao: SuperHeroDAO): LocalDataSource {


    override fun getHeros(): List<SuperHeroLocal> {
        return dao.getAllSuperheros()
    }

    override fun insertHeros(remoteSuperHeros: List<SuperHeroLocal>) {
        dao.insertAll(remoteSuperHeros)
    }

    override fun insertHero(localHero: SuperHeroLocal) {
        dao.updateSuperHero(localHero)
    }
}
