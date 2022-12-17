package com.joaquinco.androidavanzado.data.remote

import com.joaquinco.androidavanzado.data.remote.request.HerosRequest
import com.joaquinco.androidavanzado.data.remote.response.SuperHeroRemote
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(private val api: DragonBallAPI): RemoteDataSource {
    override suspend fun login(): Result<String> {
        return runCatching { api.login() }
    }

    override suspend fun getHeros(): List<SuperHeroRemote> {
        return api.getHeros(HerosRequest())
    }

    override suspend fun getHeroDetail(name: String): Result<SuperHeroRemote?> {
        return runCatching { api.getHeros(HerosRequest(name)).first()}
    }
}
