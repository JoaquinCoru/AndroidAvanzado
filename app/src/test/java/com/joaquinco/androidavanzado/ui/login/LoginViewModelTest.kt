package com.joaquinco.androidavanzado.ui.login

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.joaquinco.androidavanzado.data.LoginState
import com.joaquinco.androidavanzado.domain.Repository
import com.joaquinco.androidavanzado.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.util.Base64

class LoginViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // SUT o UUT
    private lateinit var loginViewModel: LoginViewModel

    // Dependencies
    private lateinit var repository: Repository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
    }

/*    @Test
    fun `WHEN login EXPECTS sucess return token`(){
        // GIVEN
        repository = mockk()
        sharedPreferences = mockk()
        editor = mockk()
        loginViewModel = LoginViewModel(repository, sharedPreferences)

        coEvery { repository.login() } returns  LoginState.Success("TOKEN")
        coEvery { sharedPreferences.edit() } returns  editor
        coEvery { editor.putString(any(),any()) } returns  editor
        coEvery { editor.apply() } returns  Unit

        // WHEN
        val actual = loginViewModel.login("User","Password")
        val actualLiveData = loginViewModel.stateLiveData.getOrAwaitValue()

        Truth.assertThat(actualLiveData).isEqualTo(LoginViewModel.LoginActivityState.LoginSuccess("TOKEN"))

    }*/

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}