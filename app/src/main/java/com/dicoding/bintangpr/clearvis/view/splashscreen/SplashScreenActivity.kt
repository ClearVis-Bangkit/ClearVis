package com.dicoding.bintangpr.clearvis.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bintangpr.clearvis.databinding.ActivitySplashScreenBinding
import com.dicoding.bintangpr.clearvis.view.login.LoginActivity
import com.dicoding.bintangpr.clearvis.view.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val splashViewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            splashViewModel.getUser().observe(this@SplashScreenActivity) { user ->
                if (user.accessToken.isEmpty()) {
                    Intent(this@SplashScreenActivity, LoginActivity::class.java).also { intent ->
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Intent(this@SplashScreenActivity, MainActivity::class.java).also { intent ->
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
