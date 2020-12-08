package com.tagava.repository

import com.tagava.data.Article
import com.tagava.data.LoginRequest
import com.tagava.data.LoginResponse
import com.tagava.data.VerifyOTP

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

}
