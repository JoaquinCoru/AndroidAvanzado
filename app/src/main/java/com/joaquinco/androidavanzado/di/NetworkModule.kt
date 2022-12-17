package com.joaquinco.androidavanzado.di

import android.content.Context
import android.content.SharedPreferences
import com.joaquinco.androidavanzado.data.remote.DragonBallAPI
import com.joaquinco.androidavanzado.utils.ACCESS_TOKEN_KEY
import com.joaquinco.androidavanzado.utils.LOGIN_BASIC_TOKEN_KEY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun providesHttpLoginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        sharedPreferences: SharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequestBuilder = originalRequest.newBuilder()
                    .header("Content-Type", "Application/Text")
                var newRequest: Request
                if (originalRequest.url.encodedPath.contains("api/auth/login")) {
                    newRequest = newRequestBuilder
                        .header(
                            "Authorization", "Basic ${
                                sharedPreferences.getString(
                                    LOGIN_BASIC_TOKEN_KEY, ""
                                )
                            }"
                        )
                        .build()
                } else {
                    newRequest = newRequestBuilder
                        .header(
                            "Authorization", "Bearer ${
                                sharedPreferences.getString(
                                    ACCESS_TOKEN_KEY, ""
                                )
                            }"
                        )
                        .build()
                }

                chain.proceed(newRequest)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball.keepcoding.education/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    @Provides
    fun provideAPI(retrofit: Retrofit): DragonBallAPI {
        return retrofit.create(DragonBallAPI::class.java)
    }

}