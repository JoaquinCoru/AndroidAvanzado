package com.keepcoding.androidavanzado.data

import com.keepcoding.androidavanzado.data.local.LocalDataSource
import com.keepcoding.androidavanzado.data.mappers.LocalToPresentationMapper
import com.keepcoding.androidavanzado.data.mappers.RemoteToLocalMapper
import com.keepcoding.androidavanzado.data.mappers.RemoteToPresentationMapper
import com.keepcoding.androidavanzado.data.remote.RemoteDataSource
import com.keepcoding.androidavanzado.domain.SuperHero
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

}
