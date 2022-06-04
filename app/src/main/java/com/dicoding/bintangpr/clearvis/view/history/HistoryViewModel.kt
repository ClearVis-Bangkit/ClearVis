package com.dicoding.bintangpr.clearvis.view.history

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bintangpr.clearvis.data.api.ApiConfig
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.model.DataItem
import com.dicoding.bintangpr.clearvis.data.model.UserResultHistoryResponse
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(private val pref: UserPreference) : ViewModel() {
    private val _history = MutableLiveData<List<DataItem>>()
    val history: LiveData<List<DataItem>> = _history

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getHistory(token: String, id: Int) {
        _isLoading.value = true
        val bearer = "Bearer $token"
        val client = ApiConfig.getApiService().getHistory(bearer, id)

        client.enqueue(object : Callback<UserResultHistoryResponse> {
            override fun onResponse(
                call: Call<UserResultHistoryResponse>,
                response: Response<UserResultHistoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _history.value = response.body()?.data
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResultHistoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }
}