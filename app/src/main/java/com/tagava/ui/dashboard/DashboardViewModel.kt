package com.tagava.ui.dashboard

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.DashaboardDetailsRequest
import com.tagava.data.ErrorData
import com.tagava.data.RegisterResponse
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.ui.auth.AuthViewModel
import com.tagava.util.SingleLiveEvent

class DashboardViewModel (retrofitRepository: RetrofitRepository) : ViewModel() {

    var retrofitRepository: RetrofitRepository
    var fetchDashboardDetailsStausStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var customerName: ObservableField<String>? = null
    var mobileNumber: ObservableField<String>? = null


    init {
        this.retrofitRepository = retrofitRepository
        this.customerName = ObservableField("")
        this.mobileNumber = ObservableField("")
        this.progressDialog = SingleLiveEvent<Boolean>()
    }

    fun fetchDashboardDetails() {
        progressDialog?.value = true
        var request = DashaboardDetailsRequest(
            AuthViewModel.businessIDDataLiveData.value.toString()

        )

        if (request != null) {
            this.retrofitRepository.fetchDashboardDetails(request, object : IAPICallback<Any?, ErrorData?> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: RegisterResponse? = responseData as RegisterResponse

                    response.let {
                        //  authTokenDataLiveData.value = response?.data?.get(0)?.token
                        fetchDashboardDetailsStausStatusLiveData.value = true
                    }
                }

                override fun onResponseFailure(failureData: ErrorData?) {
                    progressDialog?.value = false
                    fetchDashboardDetailsStausStatusLiveData.value = false
                }

            })
        }
    }

}