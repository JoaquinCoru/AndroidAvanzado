package com.keepcoding.androidavanzado.data.remote

import com.keepcoding.androidavanzado.data.remote.request.HerosRequest
import com.keepcoding.androidavanzado.data.remote.response.SuperHeroRemote
import com.keepcoding.androidavanzado.domain.Bootcamp
import com.keepcoding.androidavanzado.domain.SuperHero
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface DragonBallAPI {

    @GET("api/data/bootcamps")
    suspend fun getBootcamps(): List<Bootcamp>

    @POST("api/heros/all")
    suspend fun getHeros(@Body herosRequest: HerosRequest): List<SuperHeroRemote>
}
