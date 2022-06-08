package com.dicoding.bintangpr.clearvis.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import com.dicoding.bintangpr.clearvis.view.eyecheck.EyeCheckViewModel
import com.dicoding.bintangpr.clearvis.view.history.HistoryViewModel
import com.dicoding.bintangpr.clearvis.view.home.HomeViewModel
import com.dicoding.bintangpr.clearvis.view.login.LoginViewModel
import com.dicoding.bintangpr.clearvis.view.profile.ProfileViewModel
import com.dicoding.bintangpr.clearvis.view.splashscreen.SplashScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

val dataStoreModule = module {
    factory { androidContext().dataStore }
    single { UserPreference(get()) }
}

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EyeCheckViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { HomeViewModel() }
}