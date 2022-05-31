package com.dicoding.bintangpr.clearvis.view.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import com.dicoding.bintangpr.clearvis.databinding.ActivitySignupBinding
import com.dicoding.bintangpr.clearvis.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
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

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
            signupViewModel.isMessage.observe(this, {
                var message = it
                signupViewModel.isSuccess.observe(this,{
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage(message)
                        if (it){
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            setPositiveButton("Lanjut") { _, _ ->
                                finish()
                            }
                        }
                        create()
                        show()
                    }
                })

            })
        }
    }



    private fun setupAction(){
        binding.signupBtn.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.confirmPasswordEt.text.toString()
            when{
                name.isEmpty() ->{
                    binding.nameEt.error ="Masukkan Nama"
                }
                email.isEmpty() ->{
                    binding.emailEt.error ="Masukkan Email"
                }
                password.isEmpty() ->{
                    binding.passwordEt.error ="Masukkan Password"
                }
                confirmPassword.isEmpty() ->{
                    binding.confirmPasswordEt.error ="Masukkan Password Confirm"
                }
                confirmPassword != password ->{
                    binding.confirmPasswordEt.error = "Password Berbeda"
                }
                else ->{
                    signupViewModel.registerUser(name, email, password,confirmPassword)
                    signupViewModel.isLoading.observe(this, {
                        showLoading(it)
                    })

                }

            }
        }
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}