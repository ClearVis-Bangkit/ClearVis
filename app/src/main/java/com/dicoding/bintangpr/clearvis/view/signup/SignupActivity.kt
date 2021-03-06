package com.dicoding.bintangpr.clearvis.view.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bintangpr.clearvis.databinding.ActivitySignupBinding
import com.dicoding.bintangpr.clearvis.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>()
    private var isHidden = true
    private var isHiddenConfirm = true
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
            signupViewModel.isMessage.observe(this) {
                var message = it
                signupViewModel.isSuccess.observe(this) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage(message)
                        if (it) {
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
                }

            }
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
                    Toast.makeText(
                        this@SignupActivity,
                        "Please Insert your name!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                email.isEmpty() ->{
                    Toast.makeText(
                        this@SignupActivity,
                        "Please Insert your E-mail!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                password.isEmpty() ->{
                    Toast.makeText(
                        this@SignupActivity,
                        "Please Insert your Password",
                        Toast.LENGTH_LONG
                    ).show()
                }
                password.length<6 ->{
                    Toast.makeText(
                        this@SignupActivity,
                        "Password must have at least 6 characters",
                        Toast.LENGTH_LONG
                    ).show()
                }
                confirmPassword.isEmpty() ->{
                    Toast.makeText(
                        this@SignupActivity,
                        "Please Insert the Confirm Password",
                        Toast.LENGTH_LONG
                    ).show()
                }
                confirmPassword != password ->{
                    Toast.makeText(
                        this@SignupActivity,
                        "Password doesn't match!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    signupViewModel.registerUser(name, email, password, confirmPassword)
                    signupViewModel.isLoading.observe(this) {
                        showLoading(it)
                    }
                }
            }
        }
        binding.showPassword.setOnClickListener{
            if (isHidden){
                binding.passwordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isHidden = false
            } else{
                binding.passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                isHidden = true
            }
        }
        binding.showPasswordConfirm.setOnClickListener{
            if (isHiddenConfirm){
                binding.confirmPasswordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isHiddenConfirm = false
            } else{
                binding.confirmPasswordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                isHiddenConfirm = true
            }
        }

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}