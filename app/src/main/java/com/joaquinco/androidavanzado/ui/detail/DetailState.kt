package com.joaquinco.androidavanzado.ui.detail

import com.joaquinco.androidavanzado.domain.SuperHeroDetail

sealed class DetailState{
    data class Success(val hero: SuperHeroDetail) : DetailState()
    data class Failure(val error: String?): DetailState()
    data class NetworkFailure(val code: Int): DetailState()
}
