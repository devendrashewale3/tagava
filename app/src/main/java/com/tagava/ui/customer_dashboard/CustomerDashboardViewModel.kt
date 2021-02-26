package com.tagava.ui.customer_dashboard

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.tagava.R
import com.tagava.data.CustomerDashboardResponse
import com.tagava.data.DashaboardDetailsRequest
import com.tagava.data.Entry
import com.tagava.data.ErrorData
import com.tagava.repository.IAPICallback
import com.tagava.repository.RetrofitRepository
import com.tagava.ui.auth.AuthViewModel
import com.tagava.util.SingleLiveEvent

class CustomerDashboardViewModel(retrofitRepository: RetrofitRepository) : ViewModel() {


    var retrofitRepository: RetrofitRepository
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var custId: ObservableField<String>? = null
    var custName: ObservableField<String>? = null
    var totalAmtGive: ObservableField<String>? = null
    var totalAmtGot: ObservableField<String>? = null
    var ratingString: ObservableField<Float>? = null
    var makePaymentEvent: MutableLiveData<Boolean>? = null
    var receivePaymentEvent: MutableLiveData<Boolean>? = null
    var customersDataLiveData: MutableLiveData<List<Entry>> = MutableLiveData()

    var errorData: MutableLiveData<ErrorData> = MutableLiveData()


    init {
        this.retrofitRepository = retrofitRepository
        this.custId = ObservableField("")
        this.custName = ObservableField("")
        this.totalAmtGive = ObservableField("")
        this.totalAmtGot = ObservableField("")
        this.ratingString = ObservableField()
        this.progressDialog = SingleLiveEvent<Boolean>()
        this.makePaymentEvent = MutableLiveData()
        this.receivePaymentEvent = MutableLiveData()

    }

    companion object {
        var isTransactionPopupCalled: MutableLiveData<Boolean>? = MutableLiveData()
    }

    fun makePayment(view: View) {
        Navigation.findNavController(view).navigate(
                R.id.navigation_transaction, bundleOf(
                Pair("custid", custId?.get()),
                Pair("type", "0"),
                Pair("custName", custName?.get())
        )
        )
    }

    fun receivePayment(view: View) {
        Navigation.findNavController(view).navigate(
                R.id.navigation_transaction, bundleOf(
                Pair("custid", custId?.get()),
                Pair("type", "1"),
                Pair("custName", custName?.get())
        )
        )
    }


    fun fetchCustomerDashboardDetails(customerId: String) {
        progressDialog?.value = true
        var request = DashaboardDetailsRequest(
                AuthViewModel.businessSelectedIDDataLiveData.value.toString(),
                "",
                "",
                customerId,
                ""
        )

        if (request != null) {
            this.retrofitRepository.fetchCustomerDashboardDetails(
                request,
                object : IAPICallback<Any?, ErrorData?> {
                    override fun onResponseSuccess(responseData: Any?) {

                        progressDialog?.value = false
                        var response: CustomerDashboardResponse? =
                            responseData as CustomerDashboardResponse

                        response.let {
                            customersDataLiveData.value = response?.data?.get(0)?.entries
                            var totalGive = 0
                            var totalGot = 0
                            var ratingNum: Float = 0.0f

                            customersDataLiveData?.value?.forEach {
                                if (it.gaveOrGot.equals("GAVE"))
                                    totalGive += it.gaveOrGotAmount
                                else
                                    totalGot += it.gaveOrGotAmount
                                if (it.rating.length > 0)
                                    ratingNum += it.rating.toFloat()
                            }

                            var ratingStringCal: Float = (ratingNum / (response?.data?.get(0)?.entries?.size!!)).toFloat()
                            totalAmtGive?.set(totalGive.toString())
                            totalAmtGot?.set(totalGot.toString())
                            ratingString?.set(ratingStringCal)


                            Log.d("CustomerDashboardRes", response.toString())
                        }
                    }

                    override fun onResponseFailure(failureData: ErrorData?) {
                        progressDialog?.value = false
                        errorData.value = failureData
                    }

                })
        }
    }


}