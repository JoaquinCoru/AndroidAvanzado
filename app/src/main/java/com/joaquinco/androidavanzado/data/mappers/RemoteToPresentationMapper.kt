package com.joaquinco.androidavanzado.data.mappers

import com.joaquinco.androidavanzado.data.remote.response.LocationRemote
import com.joaquinco.androidavanzado.data.remote.response.SuperHeroRemote
import com.joaquinco.androidavanzado.domain.Location
import com.joaquinco.androidavanzado.domain.SuperHero
import com.joaquinco.androidavanzado.domain.SuperHeroDetail
import javax.inject.Inject

class RemoteToPresentationMapper @Inject constructor(){

    fun map(superHeroList: List<SuperHeroRemote>): List<SuperHero>{
        return superHeroList.map { map(it) }
    }

    fun map(superHero: SuperHeroRemote): SuperHero {
        return SuperHero(superHero.id, superHero.name, superHero.photo)
    }

    fun mapDetail(superHero: SuperHeroRemote): SuperHeroDetail{
        return SuperHeroDetail(superHero.id, superHero.name, superHero.description, superHero.photo, superHero.favorite)
    }

    fun mapLocations(locationsList: List<LocationRemote>): List<Location>{
        return locationsList.map { map(it) }
    }

    fun map(location: LocationRemote): Location{
        return Location(location.id,location.latitud.toDouble(), location.longitud.toDouble())
    }

}
