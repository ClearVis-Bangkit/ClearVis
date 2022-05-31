package com.dicoding.bintangpr.clearvis.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bintangpr.clearvis.data.api.ApiConfig
import com.dicoding.bintangpr.clearvis.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isMessage = MutableLiveData<String>()
    val isMessage: LiveData<String> = _isMessage

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    fun registerUser(name: String, email: String, password: String, confirmPassword:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().registerUser(name, email, password, confirmPassword)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        Log.d(TAG, "${response.body()?.message}")
                        _isMessage.value = response.body()?.message
                        _isSuccess.value = response.body()?.success
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.body()?.message}")
                    _isMessage.value = response.body()?.message
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _isMessage.value = t.message
            }
        })
    }

    companion object{
        private const val TAG = "SignupViewModel"
    }
}