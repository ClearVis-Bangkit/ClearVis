package com.dicoding.bintangpr.clearvis.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }
}