package com.tagava.repository

import com.tagava.data.*

import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("jet2/api/v1/blogs")
    fun fetchArticles(@Query("page") page: Long, @Query("limit") limit: Long = 10): Call<Article>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json"]
    )
    @POST("signUp/otp")
    fun fetchOTP(@Body request1: LoginRequest): Call<LoginResponse>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json"]
    )
    @POST("signUp/validateOtp")
    fun validateOTP(@Body request1: VerifyOTP): Call<LoginResponse>


    @POST("signUp/register")
    fun registerUser(
        @HeaderMap authHeader: Map<String, String>,
        @Body request1: RegisterRequest
    ): Call<RegisterResponse>

}
