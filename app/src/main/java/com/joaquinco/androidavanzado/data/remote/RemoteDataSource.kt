package com.joaquinco.androidavanzado.data.remote

import com.joaquinco.androidavanzado.data.remote.response.SuperHeroRemote


interface RemoteDataSource {
    suspend fun login():Result<String>
    suspend fun getHeros(): List<SuperHeroRemote>
    suspend fun getHeroDetail(name: String): Result<SuperHeroRemote>
}
