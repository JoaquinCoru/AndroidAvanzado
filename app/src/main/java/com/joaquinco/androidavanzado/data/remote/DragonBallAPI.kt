package com.joaquinco.androidavanzado.data.remote

import com.joaquinco.androidavanzado.data.remote.request.HerosRequest
import com.joaquinco.androidavanzado.data.remote.response.SuperHeroRemote
import retrofit2.http.Body
import retrofit2.http.POST

interface DragonBallAPI {

    @POST("api/auth/login")
    suspend fun login():String

    @POST("api/heros/all")
    suspend fun getHeros(@Body herosRequest: HerosRequest): List<SuperHeroRemote>

}
