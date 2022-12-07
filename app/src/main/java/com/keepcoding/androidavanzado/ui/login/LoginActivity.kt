package com.keepcoding.androidavanzado.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.keepcoding.androidavanzado.R
import com.keepcoding.androidavanzado.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        with(binding){
            btnLogin.setOnClickListener {
                viewModel.login(etEmail.text.toString(),etPassword.text.toString())
            }
        }
    }


    private fun setObservers() {
        viewModel.stateLiveData.observe(this){ loginState ->
            when(loginState){

                is LoginViewModel.LoginActivityState.InvalidUser -> {
                    Toast.makeText(this, getString(R.string.invalid_user), Toast.LENGTH_LONG).show()
                }
                is LoginViewModel.LoginActivityState.InvalidPassword -> {
                    Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_LONG).show()
                }
                else -> {
                    Log.d(LoginActivity::javaClass.name, "To implement")
                }
            }
        }
    }

}