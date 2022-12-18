package com.joaquinco.androidavanzado.data.mappers

import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal
import com.joaquinco.androidavanzado.data.remote.response.SuperHeroRemote
import javax.inject.Inject

class RemoteToLocalMapper @Inject constructor(){

    fun map(superHeroRemoteList: List<SuperHeroRemote>): List<SuperHeroLocal> {
        return superHeroRemoteList.map { map(it) }
    }

    fun map(superHeroRemote: SuperHeroRemote): SuperHeroLocal {
        return SuperHeroLocal(
            superHeroRemote.id,
            superHeroRemote.name,
            superHeroRemote.photo,
            superHeroRemote.description,
            superHeroRemote.favorite
        )
    }

}
