package com.tagava.ui.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.LoginRequest
import com.tagava.data.LoginResponse
import com.tagava.data.RegisterRequest
import com.tagava.data.VerifyOTP
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.util.SingleLiveEvent

/**
 * Created by Devendra Shewale on 07/12/20.
 */
class AuthViewModel(retrofitRepository: RetrofitRepository) : ViewModel() {
    var retrofitRepository: RetrofitRepository
    var loginDataLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var verifyOTPStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var registrationStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var mobile_number: ObservableField<String>? = null
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var otpText: ObservableField<String>? = null
    var businessName: ObservableField<String>? = null
    var userName: ObservableField<String>? = null
    var email: ObservableField<String>? = null


    init {
        this.retrofitRepository = retrofitRepository
        this.mobile_number = ObservableField("")
        this.otpText = ObservableField("")
        this.businessName = ObservableField("")
        this.userName = ObservableField("")
        this.email = ObservableField("")
        this.progressDialog = SingleLiveEvent<Boolean>()
    }

    companion object {
        var mobileNumberDataLiveData: MutableLiveData<String> = MutableLiveData()
        var authTokenDataLiveData: MutableLiveData<String> = MutableLiveData()
    }

    fun fetchLoginResponse() {
        progressDialog?.value = true
        var request = this.mobile_number?.get()?.let { LoginRequest(it) }

        if (request != null) {
            this.retrofitRepository.getOTP(request, object : IAPICallback<Any?, Any> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: LoginResponse? = responseData as LoginResponse

                    response.let {
                        loginDataLiveData.value = true
                        mobileNumberDataLiveData.value = request.mobileNo

                    }

                }

                override fun onResponseFailure(failureData: String) {
                    progressDialog?.value = false
                    loginDataLiveData.value = false
                }

            })
        }
    }

    fun fetchVerifyOTP() {
        progressDialog?.value = true
        var request = this.otpText?.get()?.let { it1 ->
            VerifyOTP(
                mobileNumberDataLiveData.value.toString(),
                it1
            )
        }

        if (request != null) {
            this.retrofitRepository.verifyOTP(request, object : IAPICallback<Any?, Any> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: LoginResponse? = responseData as LoginResponse

                    response.let {
                        authTokenDataLiveData.value = response?.data?.get(0)?.token

                        verifyOTPStatusLiveData.value = true
                    }

                }

                override fun onResponseFailure(failureData: String) {
                    progressDialog?.value = false
                    verifyOTPStatusLiveData.value = false
                }

            })
        }
    }

    fun registerUser() {
        progressDialog?.value = true
        var request = RegisterRequest(
            this.businessName?.get().toString(),
            this.email?.get().toString(),
            this.userName?.get().toString()
        )

        if (request != null) {
            this.retrofitRepository.registerUser(request, object : IAPICallback<Any?, Any> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: LoginResponse? = responseData as LoginResponse

                    response.let {
                        authTokenDataLiveData.value = response?.data?.get(0)?.token

                        registrationStatusLiveData.value = true
                    }
                }

                override fun onResponseFailure(failureData: String) {
                    progressDialog?.value = false
                    registrationStatusLiveData.value = false
                }

            })
        }
    }

}