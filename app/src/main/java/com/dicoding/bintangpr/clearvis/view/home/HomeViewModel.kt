package com.dicoding.bintangpr.clearvis.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bintangpr.clearvis.data.api.ApiConfig
import com.dicoding.bintangpr.clearvis.data.model.ArtikelsResponse
import com.dicoding.bintangpr.clearvis.data.model.DataArticle
import com.dicoding.bintangpr.clearvis.view.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listArtikels = MutableLiveData<List<DataArticle>>()
    val listArtikels: LiveData<List<DataArticle>> = _listArtikels

    fun getArtikels(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getArticles()
        client.enqueue(object : Callback<ArtikelsResponse> {
            override fun onResponse(
                call: Call<ArtikelsResponse>,
                response: Response<ArtikelsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _listArtikels.value = response.body()?.data
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<ArtikelsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "HomeFragment"
    }
}