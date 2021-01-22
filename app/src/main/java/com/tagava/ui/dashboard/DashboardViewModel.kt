package com.tagava.ui.dashboard

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.Content
import com.tagava.data.DashaboardDetailsRequest
import com.tagava.data.DashboardResponse
import com.tagava.data.ErrorData
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.ui.auth.AuthViewModel
import com.tagava.util.SingleLiveEvent

class DashboardViewModel (retrofitRepository: RetrofitRepository) : ViewModel() {

    var retrofitRepository: RetrofitRepository
    var fetchDashboardDetailsStausStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var customersDataLiveData: MutableLiveData<List<Content>> = MutableLiveData()
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
            AuthViewModel.businessSelectedIDDataLiveData.value.toString(),
            ""

        )

        if (request != null) {
            this.retrofitRepository.fetchDashboardDetails(request, object : IAPICallback<Any?, ErrorData?> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: DashboardResponse? = responseData as DashboardResponse

                    response.let {
                        customersDataLiveData.value = response?.data?.get(0)?.customers?.content
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