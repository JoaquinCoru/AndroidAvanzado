package com.keepcoding.androidavanzado.data.remote

import com.keepcoding.androidavanzado.data.remote.response.SuperHeroRemote
import com.keepcoding.androidavanzado.domain.Bootcamp


interface RemoteDataSource {
    suspend fun getBootcamps(): List<Bootcamp>
    suspend fun getHeros(): List<SuperHeroRemote>
}
