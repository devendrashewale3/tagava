package com.tagava.ui.splash

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.ErrorData
import com.tagava.data.LoginRequest
import com.tagava.data.LoginResponse
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.ui.auth.AuthViewModel
import com.tagava.util.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Devendra Shewale on 07/12/20.
 */
class SplashViewModel(retrofitRepository: RetrofitRepository) : ViewModel() {
    var retrofitRepository: RetrofitRepository
    var loginDataLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var mobile_number: ObservableField<String>? = null
    var progressDialog: SingleLiveEvent<Boolean>? = null
    val liveData: LiveData<SplashState>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<SplashState>()


    init {
        this.retrofitRepository = retrofitRepository
        this.progressDialog = SingleLiveEvent<Boolean>()


    }

    fun fetchRegResponse() {

        var token = AuthViewModel.authTokenDataLiveData.value.toString()

        if (token.isNullOrEmpty()) {
            progressDialog?.value = true
            var request = this.mobile_number?.get()?.let { LoginRequest(it) }

            if (request != null) {
                this.retrofitRepository.getOTP(request, object : IAPICallback<Any?, ErrorData?> {
                    override fun onResponseSuccess(responseData: Any?) {

                        progressDialog?.value = false
                        var response: LoginResponse? = responseData as LoginResponse

                        response.let {
                            loginDataLiveData.value = true

                        }

                    }

                    override fun onResponseFailure(failureData: ErrorData?) {
                        progressDialog?.value = false
                        loginDataLiveData.value = false
                    }

                })
            }
        } else {
            GlobalScope.launch {
                delay(1000)
                mutableLiveData.postValue(SplashState.AuthActivity())
            }
        }

    }

    fun fetchLoginResponse() {

        var token = AuthViewModel.authTokenDataLiveData.value.toString()

        if (token.isNullOrEmpty()) {
            progressDialog?.value = true
            var request = this.mobile_number?.get()?.let { LoginRequest(it) }

            if (request != null) {
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
        } else {
            GlobalScope.launch {
                delay(1000)
                mutableLiveData.postValue(SplashState.AuthActivity())
            }
        }

    }

    sealed class SplashState {
        class DashboardActivity : SplashState()
        class AuthActivity : SplashState()
    }


}