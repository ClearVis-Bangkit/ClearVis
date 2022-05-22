package com.dicoding.bintangpr.clearvis.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.bintangpr.clearvis.R
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import com.dicoding.bintangpr.clearvis.databinding.ActivityMainBinding
import com.dicoding.bintangpr.clearvis.view.factory.ViewModelFactory
import com.dicoding.bintangpr.clearvis.view.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_checkEye,
                R.id.navigation_history,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setupMainActivity()
    }

    private fun setupMainActivity(){
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this, { user ->
            if (user.accessToken!= ""){
                Toast.makeText(this, "Selamat datang ${user.name}", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }
}