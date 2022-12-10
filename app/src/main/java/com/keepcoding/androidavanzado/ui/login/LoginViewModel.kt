package com.keepcoding.androidavanzado.ui.login

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.androidavanzado.data.Repository
import com.keepcoding.androidavanzado.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val stateLiveData: MutableLiveData<LoginActivityState> by lazy {
        MutableLiveData<LoginActivityState>()
    }

    fun login(user: String, password: String) {
        if (checkUserValidity(user) && checkPasswordValidity(password)) launchLoginRequest(
            user,
            password
        )
    }

    private fun launchLoginRequest(user: String, password: String) {
       var  loginBasicToken = encodeBase64("$user:$password")
        Log.d("Basic Token", loginBasicToken)
        with(sharedPreferences){
            val editPreferences = edit()
            editPreferences.putString(LOGIN_BASIC_TOKEN_KEY,loginBasicToken)
            editPreferences.apply()
        }

        viewModelScope.launch {
            val token = withContext(Dispatchers.IO) {
                repository.login()
            }
            Log.d("TOKEN", token)

            with(sharedPreferences){
                val editPreferences = edit()
                editPreferences.putString(ACCESS_TOKEN_KEY,token)
                editPreferences.apply()
            }

        }
    }

    private fun checkUserValidity(user: String) = run {
        if (user.isBlank()) {
            setValueOnMainThread(LoginActivityState.InvalidUser)
            false
        } else
            true
    }

    private fun checkPasswordValidity(password: String) = run {
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

    fun encodeBase64(text: String): String {
        val data: ByteArray = text.toByteArray(StandardCharsets.UTF_8)

        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    sealed class LoginActivityState {
        data class LoginSuccess(val token: String) : LoginActivityState()
        data class Error(val message: String) : LoginActivityState()
        object Loading : LoginActivityState()
        object InvalidUser : LoginActivityState()
        object InvalidPassword : LoginActivityState()
    }
}