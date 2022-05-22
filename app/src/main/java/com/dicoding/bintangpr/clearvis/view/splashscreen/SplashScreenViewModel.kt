package com.dicoding.bintangpr.clearvis.view.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}