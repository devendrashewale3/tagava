package com.tagava.ui.transaction


import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tagava.data.CreatePaymentRequest
import com.tagava.data.CreatePaymentResponse
import com.tagava.data.ErrorData
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.ui.auth.AuthViewModel
import com.tagava.util.SingleLiveEvent
import java.lang.Integer.parseInt

class TransactionDialogViewModel(retrofitRepository: RetrofitRepository) : ViewModel() {
    var retrofitRepository: RetrofitRepository
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var amount: ObservableField<String>? = null
    var giveorgot: ObservableField<String>? = null
    var customerId: ObservableField<String>? = null


    init {
        this.retrofitRepository = retrofitRepository
        this.progressDialog = SingleLiveEvent<Boolean>()
        this.amount = ObservableField("")
        this.giveorgot = ObservableField("")
        this.customerId = ObservableField("")
    }

    fun createPayment() {
        progressDialog?.value = true
        var request = this.amount?.get()?.let {
            CreatePaymentRequest(
                parseInt(it),
                AuthViewModel.businessIDDataLiveData.value.toString(),
                this.customerId?.get().toString(),
                this.giveorgot?.get().toString()
            )
        }

        if (request != null) {
            this.retrofitRepository.createPayment(request, object : IAPICallback<Any?, ErrorData?> {
                override fun onResponseSuccess(responseData: Any?) {

                    progressDialog?.value = false
                    var response: CreatePaymentResponse? = responseData as CreatePaymentResponse

                    response.let {

                    }
                }

                override fun onResponseFailure(failureData: ErrorData?) {
                    progressDialog?.value = false

                }

            })
        }
    }
}