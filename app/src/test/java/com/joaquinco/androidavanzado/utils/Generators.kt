package com.joaquinco.androidavanzado.utils

import com.joaquinco.androidavanzado.data.local.model.SuperHeroLocal
import com.joaquinco.androidavanzado.data.mappers.RemoteToPresentationMapper
import com.joaquinco.androidavanzado.data.remote.response.LocationRemote
import com.joaquinco.androidavanzado.data.remote.response.SuperHeroRemote
import com.joaquinco.androidavanzado.domain.Location
import com.joaquinco.androidavanzado.domain.SuperHero
import com.joaquinco.androidavanzado.domain.SuperHeroDetail


fun generateHerosRemote(): List<SuperHeroRemote> {
    return (0 until 10).map {
        SuperHeroRemote(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            false
        )
    }
}

fun generateHerosLocal(): List<SuperHeroLocal> {
    return (0 until 10).map {
        SuperHeroLocal(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            false
        )
    }
}


fun generateHeros(): List<SuperHero> {
    return (0 until 10).map {
        SuperHero(
            "ID: $it",
            "Name $it",
            "Photo $it"
        )
    }
}

fun generateLocationsRemote(): List<LocationRemote> {
    return (0 until 10).map {
        LocationRemote(
            "ID: $it",
            "$it",
            "$it"
        )
    }
}

fun generateLocations(): List<Location> {
    return (0 until 10).map {
        Location(
            "ID: $it",
            it.toDouble(),
            it.toDouble()
        )
    }
}

fun generateSuperHeroRemote(): SuperHeroRemote {
    return SuperHeroRemote(
        "ID 0",
        "Name 0",
        "Photo 0",
        "Description 0",
        false
    )
}

fun generateSuperHeroDetail(): SuperHeroDetail{
    return RemoteToPresentationMapper().mapDetail(
        generateSuperHeroRemote()
    )
}


