package com.tagava.ui.transaction


import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tagava.data.*
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
    var transactionID: MutableLiveData<String> = MutableLiveData()
    var CustomerPaymentStatus: MutableLiveData<Boolean> = MutableLiveData()
    var RatingStatus: MutableLiveData<Boolean> = MutableLiveData()
    var errorData: MutableLiveData<ErrorData> = MutableLiveData()


    init {
        this.retrofitRepository = retrofitRepository
        this.progressDialog = SingleLiveEvent<Boolean>()
        this.amount = ObservableField("")
        this.giveorgot = ObservableField("")
        this.customerId = ObservableField("")
        CustomerPaymentStatus.value = false
        //this.amount!!.set("0")
    }

    fun createPayment() {

        if (this.amount?.get()?.length!! > 0) {
            progressDialog?.value = true
            var request = this.amount?.get()?.let {
                CreatePaymentRequest(
                    parseInt(it),
                    AuthViewModel.businessSelectedIDDataLiveData.value.toString(),
                    this.customerId?.get().toString(),
                    this.giveorgot?.get().toString()
                )
            }

            if (request != null) {
                this.retrofitRepository.createPayment(
                    request,
                    object : IAPICallback<Any?, ErrorData?> {
                        override fun onResponseSuccess(responseData: Any?) {

                            progressDialog?.value = false
                            var response: CreatePaymentResponse? =
                                responseData as CreatePaymentResponse

                            response.let {
                                transactionID.value = response?.data?.get(0)?.transactionId
                                CustomerPaymentStatus.value = true
                            }
                        }

                        override fun onResponseFailure(failureData: ErrorData?) {
                            progressDialog?.value = false
                            CustomerPaymentStatus.value = false

                        }

                    })
            }
        } else {
            var errorData = ErrorData("123", "Invalid amount");
        }
    }

    fun giveRatings(rating_string: String) {
        progressDialog?.value = true
        var request = this.amount?.get()?.let {
            RatingRequest(
                    rating_string,
                    transactionID.value.toString()
            )
        }

        if (request != null) {
            this.retrofitRepository.rateTransaction(
                    request,
                    object : IAPICallback<Any?, ErrorData?> {
                        override fun onResponseSuccess(responseData: Any?) {

                            progressDialog?.value = false
                            RatingStatus?.value = true
                            var response: RatingResponse? =
                                    responseData as RatingResponse

                            response.let {

                            }
                        }

                        override fun onResponseFailure(failureData: ErrorData?) {
                            progressDialog?.value = false
                            RatingStatus?.value = false

                        }

                    })
        }
    }
}