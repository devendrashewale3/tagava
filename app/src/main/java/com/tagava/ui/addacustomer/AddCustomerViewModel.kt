package com.tagava.ui.addacustomer

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.AddCustomerRequest
import com.tagava.data.ErrorData
import com.tagava.data.RegisterResponse
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.util.SingleLiveEvent

class AddCustomerViewModel(retrofitRepository: RetrofitRepository) : ViewModel() {

    var retrofitRepository: RetrofitRepository
    var addCustomerStausStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var customerName: ObservableField<String>? = null
    var mobileNumber: ObservableField<String>? = null


    init {
        this.retrofitRepository = retrofitRepository
        this.customerName = ObservableField("")
        this.mobileNumber = ObservableField("")

        this.progressDialog = SingleLiveEvent<Boolean>()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


    fun addCustomer() {
        progressDialog?.value = true
        var request = AddCustomerRequest(
            this.mobileNumber?.get().toString(),
            this.customerName?.get().toString()
        )

        if (request != null) {
            this.retrofitRepository.addCustomer(request, object : IAPICallback<Any?, ErrorData?> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: RegisterResponse? = responseData as RegisterResponse

                    response.let {
                        //  authTokenDataLiveData.value = response?.data?.get(0)?.token
                        addCustomerStausStatusLiveData.value = true
                    }
                }

                override fun onResponseFailure(failureData: ErrorData?) {
                    progressDialog?.value = false
                    addCustomerStausStatusLiveData.value = false
                }

            })
        }
    }
}