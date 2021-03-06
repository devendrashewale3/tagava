package com.tagava.ui.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.BusinessAllResponse
import com.tagava.data.ErrorData
import com.tagava.data.RegisterRequest
import com.tagava.data.RegisterResponse
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.util.SingleLiveEvent

/**
 * Created by Devendra Shewale on 07/12/20.
 */
class RegisterViewModel(retrofitRepository: RetrofitRepository) : ViewModel() {
    var retrofitRepository: RetrofitRepository
    var registrationStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var businessName: ObservableField<String>? = null
    var userName: ObservableField<String>? = null
    var email: ObservableField<String>? = null
    var errorData: MutableLiveData<ErrorData> = MutableLiveData()


    init {
        this.retrofitRepository = retrofitRepository
        this.businessName = ObservableField("")
        this.userName = ObservableField("")
        this.email = ObservableField("")
        this.progressDialog = SingleLiveEvent<Boolean>()
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
                    var response: RegisterResponse? = responseData as RegisterResponse

                    response.let {

                        AuthViewModel.businessSelectedIDDataLiveData.value = response?.data?.get(0)?.businessId
                        fetchAllBusiness()
                    }
                }

                override fun onResponseFailure(failureData: ErrorData?) {
                    errorData.value = failureData
                    progressDialog?.value = false
                    registrationStatusLiveData.value = false
                }

            })
        }
    }


    fun fetchAllBusiness() {
        this.retrofitRepository.fetchAllBusinessData(object : IAPICallback<Any?, ErrorData?> {
            override fun onResponseSuccess(responseData: Any?) {

                progressDialog?.value = false
                var response: BusinessAllResponse? = responseData as BusinessAllResponse

                response.let {
//                    AuthViewModel.businessSelectedIDDataLiveData.value =
//                        response?.data?.get(0)?.businessId
                    AuthViewModel.businessIDDataLiveData.value = response?.data
                    registrationStatusLiveData.value = true
                }
            }

            override fun onResponseFailure(failureData: ErrorData?) {
                errorData.value = failureData
                progressDialog?.value = false
                registrationStatusLiveData.value = false
            }

        })
    }

}