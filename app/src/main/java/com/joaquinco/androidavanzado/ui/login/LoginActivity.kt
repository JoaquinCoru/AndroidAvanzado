package com.joaquinco.androidavanzado.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.joaquinco.androidavanzado.R
import com.joaquinco.androidavanzado.databinding.ActivityLoginBinding
import com.joaquinco.androidavanzado.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                is LoginViewModel.LoginActivityState.Error -> {
                    showLoading(false)
                    Toast.makeText(this,"Error: ${loginState.message}",Toast.LENGTH_LONG).show()
                }
                is LoginViewModel.LoginActivityState.Loading -> {
                    showLoading(true)
                }
                is LoginViewModel.LoginActivityState.LoginSuccess -> {
                    showLoading(false)
                    goToHomeActivity()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun goToHomeActivity(){
        HomeActivity.start(this)
        finish()
    }

}