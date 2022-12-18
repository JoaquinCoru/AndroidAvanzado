package com.joaquinco.androidavanzado.ui.detail

import com.joaquinco.androidavanzado.domain.SuperHeroDetail

sealed class LikeState{
    data class Success(val message: String = "Success") : LikeState()
    data class Failure(val error: String?): LikeState()
    data class NetworkFailure(val code: Int): LikeState()
}
