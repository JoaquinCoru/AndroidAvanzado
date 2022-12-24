package com.joaquinco.androidavanzado.fakes

import android.util.Log
import com.joaquinco.androidavanzado.data.remote.RemoteDataSource
import com.joaquinco.androidavanzado.data.remote.response.LocationRemote
import com.joaquinco.androidavanzado.data.remote.response.SuperHeroRemote
import com.joaquinco.androidavanzado.utils.generateHerosRemote
import com.joaquinco.androidavanzado.utils.generateLocationsRemote
import com.joaquinco.androidavanzado.utils.generateSuperHeroDetail
import com.joaquinco.androidavanzado.utils.generateSuperHeroRemote
import retrofit2.HttpException
import retrofit2.Response

class FakeRemoteDataSource: RemoteDataSource {

    override suspend fun login(): Result<String> {
        return  Result.success("TOKEN")
    }

    override suspend fun getHeros(): List<SuperHeroRemote> {
        return generateHerosRemote()
    }

    override suspend fun getHeroDetail(name: String): Result<SuperHeroRemote?> {
        return when(name){
            "SUCCESS" -> Result.success(SuperHeroRemote("id", "Maestro Roshi", "photo", "description", false))
            "NETWORK_ERROR" -> Result.failure(HttpException(Response.success(204, {})))
            "NULL"-> Result.failure(NullPointerException("Null pointer error"))
            "SUCCESS_BUT_NULL"-> Result.success(null)
            else -> {Result.failure(Exception())}
        }
    }

    override suspend fun getLocations(id: String): List<LocationRemote> {
        return generateLocationsRemote()
    }

    override suspend fun setLike(id: String): Result<Unit> {
        return Result.success(println("Guardado con éxito"))
    }
}