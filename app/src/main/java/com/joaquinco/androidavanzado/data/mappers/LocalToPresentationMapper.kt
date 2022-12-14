package com.joaquinco.androidavanzado.data.mappers

import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal
import com.joaquinco.androidavanzado.domain.SuperHero
import com.joaquinco.androidavanzado.domain.SuperHeroDetail
import javax.inject.Inject

class LocalToPresentationMapper @Inject constructor(){

    fun map(superHeroList: List<SuperHeroLocal>): List<SuperHero>{
        return superHeroList.map { map(it) }
    }

    fun map(superHero: SuperHeroLocal): SuperHero {
        return SuperHero(superHero.id, superHero.name, superHero.photo)
    }

    fun  map(superHeroDetail: SuperHeroDetail): SuperHeroLocal{
        return  SuperHeroLocal(superHeroDetail.id, superHeroDetail.name,superHeroDetail.photo, superHeroDetail.description, superHeroDetail.favorite)
    }

}
