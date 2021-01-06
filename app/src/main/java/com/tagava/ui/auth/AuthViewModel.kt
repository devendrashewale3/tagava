package com.tagava.ui.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.*
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
    var verifyLoginOTPStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
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
        var businessIDDataLiveData: MutableLiveData<String> = MutableLiveData()
        var authTokenDataLiveData: MutableLiveData<String> = MutableLiveData()
        var authMobileNumberDataLiveData: MutableLiveData<String> = MutableLiveData()
        var isUserRegistered: MutableLiveData<Boolean> = MutableLiveData()
    }

    fun fetchLoginResponse() {
        progressDialog?.value = true
        var request = this.mobile_number?.get()?.let { LoginRequest(it) }
        mobileNumberDataLiveData.value = request?.mobileNo
        if (request != null) {
            this.retrofitRepository.getLoginOTP(request, object : IAPICallback<Any?, ErrorData?> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: LoginResponse? = responseData as LoginResponse

                    response.let {
                        loginDataLiveData.value = true

                        isUserRegistered.value = false

                    }

                }

                override fun onResponseFailure(failureData: ErrorData?) {
                    progressDialog?.value = false
                    loginDataLiveData.value = false

                    if (failureData?.code.equals("LG-INV-MB-002")) {
                        isUserRegistered.value = true
                        fetchRegOTP(request)
                    } else {

                    }
                }

            })
        }
    }

    fun fetchRegOTP(request: LoginRequest) {
        this.retrofitRepository.getOTP(request, object : IAPICallback<Any?, ErrorData?> {
            override fun onResponseSuccess(responseData: Any?) {

                progressDialog?.value = false
                var response: LoginResponse? = responseData as LoginResponse

                response.let {
                    loginDataLiveData.value = true

                }

            }

            override fun onResponseFailure(ffailureData: ErrorData?) {
                progressDialog?.value = false
                loginDataLiveData.value = false
            }

        })
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
            this.retrofitRepository.verifyOTP(
                isUserRegistered.value!!,
                request,
                object : IAPICallback<Any?, ErrorData?> {
                    override fun onResponseSuccess(responseData: Any?) {

                        progressDialog?.value = false
                        var response: LoginResponse? = responseData as LoginResponse

                        response.let {
                            authTokenDataLiveData.value = response?.data?.get(0)?.token
                            authMobileNumberDataLiveData.value =
                                mobileNumberDataLiveData.value.toString()
                            if (isUserRegistered.value!!)
                                verifyOTPStatusLiveData.value = true
                            else verifyLoginOTPStatusLiveData.value = true
                    }

                }

                    override fun onResponseFailure(failureData: ErrorData?) {
                        progressDialog?.value = false
                        if (isUserRegistered.value!!)
                            verifyOTPStatusLiveData.value = false
                        else verifyLoginOTPStatusLiveData.value = false
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
            this.retrofitRepository.registerUser(request, object : IAPICallback<Any?, ErrorData?> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: LoginResponse? = responseData as LoginResponse

                    response.let {
                        authTokenDataLiveData.value = response?.data?.get(0)?.token

                        registrationStatusLiveData.value = true
                    }
                }

                override fun onResponseFailure(failureData: ErrorData?) {
                    progressDialog?.value = false
                    registrationStatusLiveData.value = false
                }

            })
        }
    }

}