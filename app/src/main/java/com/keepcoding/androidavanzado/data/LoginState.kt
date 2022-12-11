package com.keepcoding.androidavanzado.data

sealed class LoginState{
    data class Success(val token:String): LoginState()
    data class Failure(val error:String?):LoginState()
    data class NetworkFailure(val code:Int, val message:String?): LoginState()
}
