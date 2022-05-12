package com.dicoding.bintangpr.clearvis.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.bintangpr.clearvis.R
import com.dicoding.bintangpr.clearvis.view.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }
}