package com.tagava.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.tagava.data.*
import com.tagava.ui.auth.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.String

class RetrofitRepository {
    private val apiService: APIService
    var retrofit: Retrofit? = null


    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl(BaseURL.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit?.create(APIService::class.java)!!

    }

    fun getOTP(request: LoginRequest, iapiCallback: IAPICallback<*, *>) {

        val call: Call<LoginResponse> = apiService.fetchOTP(request)
        call.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful()) {
                    val loginResponse: LoginResponse? = response.body()
                    Log.d(
                        "Response",
                        String.valueOf(loginResponse?.data?.get(0)?.otp)
                    )
                    iapiCallback.onResponseSuccess(loginResponse)
                } else {
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure("error")
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>,
                t: Throwable
            ) {
                iapiCallback.onResponseFailure("error")
            }
        })
    }


    fun verifyOTP(request: VerifyOTP, iapiCallback: IAPICallback<*, *>) {

        val call: Call<LoginResponse> = apiService.validateOTP(request)
        call.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful()) {
                    val loginResponse: LoginResponse? = response.body()
                    Log.d(
                        "Response",
                        String.valueOf(loginResponse?.data?.get(0)?.otp)
                    )
                    iapiCallback.onResponseSuccess(loginResponse)
                } else {
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure("error")
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>,
                t: Throwable
            ) {
                iapiCallback.onResponseFailure("error")
            }
        })
    }

    fun registerUser(request: RegisterRequest, iapiCallback: IAPICallback<*, *>) {

        var token = "Bearer " + AuthViewModel.authTokenDataLiveData.value.toString()
        val headerMap: HashMap<kotlin.String, kotlin.String> = HashMap()
        headerMap["Authorization"] = token

        headerMap["ContentType"] = "Application/json"


        val call: Call<RegisterResponse> = apiService.registerUser(headerMap, request)
        call.enqueue(object : Callback<RegisterResponse?> {
            override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                iapiCallback.onResponseFailure("error")
            }

            override fun onResponse(
                call: Call<RegisterResponse?>,
                response: Response<RegisterResponse?>
            ) {


                if (response.isSuccessful()) {
                    val loginResponse: RegisterResponse? = response.body()
                    Log.d(
                        "Response",
                        String.valueOf(loginResponse?.toString())
                    )
                    iapiCallback.onResponseSuccess(loginResponse)
                } else {
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure("error")
                }
            }

        })
    }

}
