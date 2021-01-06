package com.tagava.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
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
                    val gson = Gson()
                    val type = object : TypeToken<LoginResponse>() {}.type
                    var errorResponse: LoginResponse? =
                        gson.fromJson(response.errorBody()!!.charStream(), type)
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure(errorResponse?.error?.get(0))
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>,
                t: Throwable
            ) {
                val error = ErrorData("error", "Generic error")
                iapiCallback.onResponseFailure(error)
            }
        })
    }


    fun getLoginOTP(request: LoginRequest, iapiCallback: IAPICallback<*, *>) {

        val call: Call<LoginResponse> = apiService.fetchLoginOTP(request)
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
                    val gson = Gson()
                    val type = object : TypeToken<LoginResponse>() {}.type
                    var errorResponse: LoginResponse? =
                        gson.fromJson(response.errorBody()!!.charStream(), type)
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure(errorResponse?.error?.get(0))
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>,
                t: Throwable
            ) {
                val error = ErrorData("error", "Generic error")
                iapiCallback.onResponseFailure(error)
            }
        })
    }


    fun verifyOTP(isUserRegistered: Boolean, request: VerifyOTP, iapiCallback: IAPICallback<*, *>) {

        val call: Call<LoginResponse> by lazy {
            if (isUserRegistered)
                apiService.validateOTP(request)
            else
                apiService.validateLoginOTP(request)
        }
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
                    val gson = Gson()
                    val type = object : TypeToken<LoginResponse>() {}.type
                    var errorResponse: LoginResponse? =
                        gson.fromJson(response.errorBody()!!.charStream(), type)
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure(errorResponse?.error?.get(0))
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>,
                t: Throwable
            ) {
                val error = ErrorData("error", "Generic error")
                iapiCallback.onResponseFailure(error)
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
                val error = ErrorData("error", "Generic error")
                iapiCallback.onResponseFailure(error)
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
                    val gson = Gson()
                    val type = object : TypeToken<LoginResponse>() {}.type
                    var errorResponse: LoginResponse? =
                        gson.fromJson(response.errorBody()!!.charStream(), type)
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure(errorResponse?.error?.get(0))
                }
            }

        })
    }

    fun addCustomer(request: AddCustomerRequest, iapiCallback: IAPICallback<*, *>) {

        var token = "Bearer " + AuthViewModel.authTokenDataLiveData.value.toString()
        val headerMap: HashMap<kotlin.String, kotlin.String> = HashMap()
        headerMap["Authorization"] = token

        headerMap["ContentType"] = "Application/json"


        val call: Call<RegisterResponse> = apiService.addCustomerAPI(headerMap, request)
        call.enqueue(object : Callback<RegisterResponse?> {
            override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                val error = ErrorData("error", "Generic error")
                iapiCallback.onResponseFailure(error)
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
                    val loginResponse: RegisterResponse? = response.body()
                    val addCustomerError = loginResponse?.error?.get(0)
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure(addCustomerError)
                }
            }

        })
    }

    fun fetchDashboardDetails(request: DashaboardDetailsRequest, iapiCallback: IAPICallback<*, *>) {

        var token = "Bearer " + AuthViewModel.authTokenDataLiveData.value.toString()
        val headerMap: HashMap<kotlin.String, kotlin.String> = HashMap()
        headerMap["Authorization"] = token

        headerMap["ContentType"] = "Application/json"


        val call: Call<RegisterResponse> = apiService.fetchDashboardDetailsAPI(headerMap, request)
        call.enqueue(object : Callback<RegisterResponse?> {
            override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                val error = ErrorData("error", "Generic error")
                iapiCallback.onResponseFailure(error)
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
                    val loginResponse: RegisterResponse? = response.body()
                    val addCustomerError = loginResponse?.error?.get(0)
                    Log.e("error ", response.errorBody().toString())
                    iapiCallback.onResponseFailure(addCustomerError)
                }
            }

        })
    }

}
