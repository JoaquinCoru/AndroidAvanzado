package com.joaquinco.androidavanzado.ui.login

import android.content.SharedPreferences
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaquinco.androidavanzado.data.LoginState
import com.joaquinco.androidavanzado.data.Repository
import com.joaquinco.androidavanzado.utils.*
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
       val loginBasicToken = encodeBase64("$user:$password")

        with(sharedPreferences){
            val editPreferences = edit()
            editPreferences.putString(LOGIN_BASIC_TOKEN_KEY,loginBasicToken)
            editPreferences.apply()
        }
        setValueOnMainThread(LoginActivityState.Loading)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.login()
            }

            when (result){
                is LoginState.Success -> {
                    setValueOnMainThread(LoginActivityState.LoginSuccess(result.token))
                    with(sharedPreferences){
                        val editPreferences = edit()
                        editPreferences.putString(ACCESS_TOKEN_KEY,result.token)
                        editPreferences.apply()
                    }
                }
                is LoginState.NetworkFailure -> setValueOnMainThread(LoginActivityState.Error("${result.code}: ${result.message}"))
                is LoginState.Failure -> {setValueOnMainThread(LoginActivityState.Error(result.error ?: ""))}
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

    private fun setValueOnMainThread(value: LoginActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            stateLiveData.value = value
        }
    }

    private fun encodeBase64(text: String): String {
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