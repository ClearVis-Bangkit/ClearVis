package com.dicoding.bintangpr.clearvis.view.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import com.dicoding.bintangpr.clearvis.databinding.ActivityLoginBinding
import com.dicoding.bintangpr.clearvis.view.factory.ViewModelFactory
import com.dicoding.bintangpr.clearvis.view.main.MainActivity
import com.dicoding.bintangpr.clearvis.view.signup.SignupActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupView()
        setupAction()
    }
    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
            setupViewModelLogin()
        }
    }

    private fun setupViewModelLogin(){
        loginViewModel.getUser().observe(this, { user ->

            if (user.accessToken!= ""){
                alertBuilderConfirm()

            } else {
                alertBuilderFalse()
            }
        })
    }

    private fun alertBuilderFalse(){
        AlertDialog.Builder(this).apply {
            setTitle("Maaf!")
            setMessage("email atau passwordmu salah, silahkan masukkan lagi email dan passwordmu")
            setPositiveButton("Lanjut") { _, _ ->

            }
            create()
            show()
        }
    }

    private fun alertBuilderConfirm(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Selamat Datang mari periksa matamu")
            setPositiveButton("Lanjut") { _, _ ->

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
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
                    binding.emailEt.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                password.length<6 ->{
                    binding.passwordEditTextLayout.error = "Password minimal memiliki 6 karakter"
                }
                else -> {
                    loginViewModel.loginUser(email, password)
                    loginViewModel.isLoading.observe(this, {
                        showLoading(it)
                    })

                }
            }
        }
        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}