package com.dicoding.bintangpr.clearvis.view.main

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    companion object{
        private const val TAG = "MainViewModel"
        private var TOKEN = ""
    }
}