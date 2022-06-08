package com.dicoding.bintangpr.clearvis.view.main

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.bintangpr.clearvis.data.api.ApiConfig
import com.dicoding.bintangpr.clearvis.data.model.ArtikelsResponse
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.model.DataArticle
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }




    companion object{
        private const val TAG = "MainViewModel"
        private var TOKEN = ""
    }
}