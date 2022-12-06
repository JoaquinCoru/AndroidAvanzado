package com.keepcoding.androidavanzado.data.remote

import com.keepcoding.androidavanzado.data.remote.request.HerosRequest
import com.keepcoding.androidavanzado.data.remote.response.SuperHeroRemote
import com.keepcoding.androidavanzado.domain.Bootcamp
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(private val api: DragonBallAPI): RemoteDataSource {

    override suspend fun getBootcamps(): List<Bootcamp> {
        return api.getBootcamps()
    }

    override suspend fun getHeros(): List<SuperHeroRemote> {
        return api.getHeros(HerosRequest())
    }
}
