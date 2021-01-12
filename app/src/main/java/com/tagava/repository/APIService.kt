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

    @POST("login/otp")
    fun fetchLoginOTP(@Body request1: LoginRequest): Call<LoginResponse>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json"]
    )
    @POST("signUp/validateOtp")
    fun validateOTP(@Body request1: VerifyOTP): Call<LoginResponse>

    @POST("login/validateOtp")
    fun validateLoginOTP(@Body request1: VerifyOTP): Call<LoginResponse>


    @POST("signUp/register")
    fun registerUser(
        @HeaderMap authHeader: Map<String, String>,
        @Body request1: RegisterRequest
    ): Call<RegisterResponse>

    @POST("customer/create")
    fun addCustomerAPI(
        @HeaderMap headerMap: HashMap<String, String>,
        @Body request: AddCustomerRequest
    ): Call<RegisterResponse>

    @GET("dashboard/business")
    fun fetchDashboardDetailsAPI(
        @HeaderMap headerMap: HashMap<String, String>,
        @Query("businessId") businessId: String,
        @Query("searsearchByNameOrMobile") searsearchByNameOrMobile: String
    ): Call<DashboardResponse>

    @GET("business/getAll")
    fun fetchAllBusinessAPI(@HeaderMap headerMap: HashMap<String, String>): Call<BusinessAllResponse>


    @POST("transaction/create")
    fun createPaymentAPI(
        @HeaderMap authHeader: Map<String, String>,
        @Body request: CreatePaymentRequest
    ): Call<CreatePaymentResponse>

}
