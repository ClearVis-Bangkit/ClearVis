package com.dicoding.bintangpr.clearvis.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bintangpr.clearvis.databinding.ActivityLoginBinding
import com.dicoding.bintangpr.clearvis.view.main.MainActivity
import com.dicoding.bintangpr.clearvis.view.signup.SignupActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
            setupViewModelLogin()
        }
    }

    private fun setupViewModelLogin() {
        loginViewModel.getUser().observe(this) { user ->
            if (user.accessToken.isNotEmpty()) {
                alertBuilderConfirm()
            } else {
                alertBuilderFalse()
            }
        }
    }

    private fun alertBuilderFalse(){
        Toast.makeText(
            this@LoginActivity,
            "wrong email or password",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun alertBuilderConfirm() {
        Toast.makeText(
            this@LoginActivity,
            "Login Success",
            Toast.LENGTH_LONG
        ).show()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction(){
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEditTextLayout.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEt.error = "Insert E-Mail"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Insert Password"
                }
                password.length<6 ->{
                    binding.passwordEditTextLayout.error =
                        "Password must have at least 6 characters"
                }
                else -> {
                    loginViewModel.loginUser(email, password)
                    loginViewModel.isLoading.observe(this) {
                        showLoading(it)
                    }

                }
            }
        }
        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}