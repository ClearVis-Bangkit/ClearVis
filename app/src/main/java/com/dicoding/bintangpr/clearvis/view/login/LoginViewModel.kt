package com.dicoding.bintangpr.clearvis.view.login

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.bintangpr.clearvis.data.api.ApiConfig
import com.dicoding.bintangpr.clearvis.data.model.Data
import com.dicoding.bintangpr.clearvis.data.model.LoginResponse
import com.dicoding.bintangpr.clearvis.data.preference.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginUser(email: String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        viewModelScope.launch {
                            response.body()?.data?.let { pref.saveUser(it) }
                            _isLoading.value = false
                        }
                        Log.d(TAG, "${response.body()?.success}")
                    }
                }else{
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.body()?.success}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<Data> {
        return pref.getUser().asLiveData()
    }

    companion object{
        private const val TAG = "SignupViewModel"
    }
}