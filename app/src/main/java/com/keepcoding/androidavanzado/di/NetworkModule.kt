package com.keepcoding.androidavanzado.di

import android.content.Context
import androidx.room.Room
import com.keepcoding.androidavanzado.data.local.SuperHeroDAO
import com.keepcoding.androidavanzado.data.local.SuperHeroDatabase
import com.keepcoding.androidavanzado.data.remote.DragonBallAPI
import com.keepcoding.androidavanzado.ui.superherolist.SuperHeroListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun providesHttpLoginInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer $TOKEN")
                    .header("Content-Type", "Application/Text")
                    .build()
                chain.proceed(newRequest)
            }
            .authenticator { _, response ->
                response.request.newBuilder().header("Authorization", "Bearer ${SuperHeroListViewModel.TOKEN}").build()
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dragonball.keepcoding.education/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideAPI(retrofit:Retrofit):DragonBallAPI{
        return retrofit.create(DragonBallAPI::class.java)
    }

}