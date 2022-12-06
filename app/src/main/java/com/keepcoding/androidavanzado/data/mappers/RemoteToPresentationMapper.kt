package com.keepcoding.androidavanzado.data.mappers

import com.keepcoding.androidavanzado.data.remote.response.SuperHeroRemote
import com.keepcoding.androidavanzado.domain.SuperHero
import javax.inject.Inject

class RemoteToPresentationMapper @Inject constructor(){

    fun map(superHeroList: List<SuperHeroRemote>): List<SuperHero>{
        return superHeroList.map { map(it) }
    }

    fun map(superHero: SuperHeroRemote): SuperHero {
        return SuperHero(superHero.id, superHero.name, superHero.photo)
    }

}
