package com.dicoding.bintangpr.clearvis.data.api
import com.dicoding.bintangpr.clearvis.data.model.LoginResponse
import com.dicoding.bintangpr.clearvis.data.model.RegisterResponse
import com.dicoding.bintangpr.clearvis.data.model.UploadHistoryResponse
import com.dicoding.bintangpr.clearvis.data.model.UserResultHistoryResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.7:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("history")
    fun uploadHistory(
        @Header("Authorization") token: String,
        @Part("userId") userId: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("status") status: RequestBody
    ): Call<UploadHistoryResponse>

    @GET("history")
    fun getHistory(
        @Header("Authorization") token: String,
        @Query("userId") id: Int
    ): Call<UserResultHistoryResponse>
}