package com.joaquinco.androidavanzado.data

import com.joaquinco.androidavanzado.data.local.LocalDataSource
import com.joaquinco.androidavanzado.data.mappers.LocalToPresentationMapper
import com.joaquinco.androidavanzado.data.mappers.RemoteToLocalMapper
import com.joaquinco.androidavanzado.data.mappers.RemoteToPresentationMapper
import com.joaquinco.androidavanzado.data.remote.RemoteDataSource
import com.joaquinco.androidavanzado.domain.Location
import com.joaquinco.androidavanzado.domain.Repository
import com.joaquinco.androidavanzado.domain.SuperHero
import com.joaquinco.androidavanzado.ui.detail.DetailState
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val remoteToPresentationMapper: RemoteToPresentationMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper,
    private val localToPresentationMapper: LocalToPresentationMapper
): Repository {

    override suspend fun login(): LoginState {
        val result =  remoteDataSource.login()

        return when{
            result.isSuccess -> LoginState.Success(result.getOrThrow())
            else ->{
                when (val exception = result.exceptionOrNull()){
                    is HttpException -> LoginState.NetworkFailure(
                        exception.code(),
                        exception.message
                    )
                    else -> {
                        LoginState.Failure(
                            result.exceptionOrNull()?.message
                        )
                    }
                }
            }
        }
    }

    override suspend fun getHeros(): List<SuperHero> {
        return remoteToPresentationMapper.map(remoteDataSource.getHeros())
    }

    override suspend fun getHerosWithCache(): List<SuperHero> {
        // 1º Pido los datos a local
        val localSuperHeros = localDataSource.getHeros()
        // 2º Compruebo si hay datos
        if (localSuperHeros.isEmpty()) {
            // 3º Si no hay datos
            //3a Pido datos a remoto
            val remoteSuperHeros = remoteDataSource.getHeros()
            //3b Guardo datos en local
            localDataSource.insertHeros(remoteToLocalMapper.map(remoteSuperHeros))
        }
        // 4º Devuelvo datos local
        return localToPresentationMapper.map(localDataSource.getHeros())
    }

    override suspend fun getHeroDetail(name: String): DetailState {
        val result = remoteDataSource.getHeroDetail(name)
        return when {
            result.isSuccess -> {
                result.getOrNull()?.let { DetailState.Success(remoteToPresentationMapper.mapDetail(it)) }
                    ?: DetailState.Failure(
                        result.exceptionOrNull()?.message
                    )
            }
            else -> {
                when (val exception = result.exceptionOrNull()) {
                    is HttpException -> DetailState.NetworkFailure(
                        exception.code()
                    )
                    else -> {
                        DetailState.Failure(
                            result.exceptionOrNull()?.message
                        )
                    }
                }
            }
        }
    }

    override suspend fun getLocations(id: String): List<Location> {
        return remoteToPresentationMapper.mapLocations(remoteDataSource.getLocations(id))
    }

}
