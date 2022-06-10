package com.dicoding.bintangpr.clearvis.view.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference

class SplashScreenViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }

}