package com.dicoding.bintangpr.clearvis.view.eyecheck

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bintangpr.clearvis.data.api.ApiConfig
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.model.UploadHistoryResponse
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EyeCheckViewModel(private val pref: UserPreference) : ViewModel() {
    private val _uploadResponse = MutableLiveData<UploadHistoryResponse>()
    val uploadResponse: LiveData<UploadHistoryResponse> = _uploadResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadHistory(
        token: String,
        userId: RequestBody,
        file: MultipartBody.Part,
        status: RequestBody
    ) {
        _isLoading.value = true
        val bearer = "Bearer $token"
        val client = ApiConfig.getApiService().uploadHistory(bearer, userId, file, status)

        client.enqueue(object : Callback<UploadHistoryResponse> {
            override fun onResponse(
                call: Call<UploadHistoryResponse>,
                response: Response<UploadHistoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _uploadResponse.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e("EyeCheckViewModel", "onSuccess: ${response.body()?.success}")
                }
            }

            override fun onFailure(call: Call<UploadHistoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("EyeCheckViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }
}