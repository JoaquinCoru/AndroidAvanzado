package com.joaquinco.androidavanzado.di

import com.joaquinco.androidavanzado.data.Repository
import com.joaquinco.androidavanzado.data.RepositoryImpl
import com.joaquinco.androidavanzado.data.local.LocalDataSource
import com.joaquinco.androidavanzado.data.local.LocalDataSourceImpl
import com.joaquinco.androidavanzado.data.remote.RemoteDataSource
import com.joaquinco.androidavanzado.data.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindRemoteDatasource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}