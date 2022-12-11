package com.keepcoding.androidavanzado.data.remote

import com.keepcoding.androidavanzado.data.remote.response.SuperHeroRemote


interface RemoteDataSource {
    suspend fun login():Result<String>
    suspend fun getHeros(): List<SuperHeroRemote>
}
