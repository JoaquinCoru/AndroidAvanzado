package com.keepcoding.androidavanzado.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel(){

    val stateLiveData : MutableLiveData<LoginActivityState> by lazy {
        MutableLiveData<LoginActivityState>()
    }

    fun login(user: String, password: String) {
        if (checkUserValidity(user) && checkPasswordValidity(password)) launchLoginRequest(user, password)
    }

    private fun launchLoginRequest(user: String,password: String){

    }

    private fun checkUserValidity(user: String) = run {
        if (user.isBlank()) {
            setValueOnMainThread(LoginActivityState.InvalidUser)
            false
        } else
            true
    }

    private fun checkPasswordValidity(password: String)= run {
        if (password.isBlank() && password.length < 3) {
            setValueOnMainThread(LoginActivityState.InvalidPassword)
            false
        } else
            true
    }

    fun setValueOnMainThread(value: LoginActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            stateLiveData.value = value
        }
    }

    sealed class LoginActivityState {
        data class LoginSuccess(val token: String) : LoginActivityState()
        data class Error(val message : String): LoginActivityState()
        object Loading : LoginActivityState()
        object InvalidUser : LoginActivityState()
        object InvalidPassword : LoginActivityState()
    }
}