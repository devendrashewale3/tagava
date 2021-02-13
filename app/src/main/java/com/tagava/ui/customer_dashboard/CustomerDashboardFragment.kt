package com.tagava.ui.customer_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tagava.R
import com.tagava.data.Entry
import com.tagava.databinding.FragmentCustomerDashboardBinding
import com.tagava.util.CustomeProgressDialog
import kotlinx.android.synthetic.main.fragment_customer_dashboard.view.*


/**
 * Created by Devendra Shewale on 23/12/20.
 */
class CustomerDashboardFragment : Fragment() {

    private lateinit var customerDashboardViewModel: CustomerDashboardViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: Recycler_CustomerView_Adapter
    private lateinit var custId: String
    private lateinit var custName: String
    var customeProgressDialog: CustomeProgressDialog? = null

    var binding: FragmentCustomerDashboardBinding? = null

    var data: ArrayList<Entry> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val navigationSpinner = activity?.findViewById<Spinner>(R.id.spinner_nav)
        navigationSpinner?.visibility = View.INVISIBLE
        this.customerDashboardViewModel.fetchCustomerDashboardDetails(custId)
        this.customerDashboardViewModel.makePaymentEvent?.value = false
        this.customerDashboardViewModel.receivePaymentEvent?.value = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        custId = arguments?.getString("custid").toString()
        custName = arguments?.getString("custName").toString()


        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_customer_dashboard, container, false
        )

        initViewModel()
        return binding?.root

    }


    private fun initViewModel() {

        var customerDashboardViewModelFactory = CustomerDashboardViewModelFactory()
        this.customerDashboardViewModel =
                activity?.let {
                    ViewModelProviders.of(it, customerDashboardViewModelFactory)
                            .get(CustomerDashboardViewModel::class.java)
                }!!
        binding?.viewmodelCustomerDashboard = this.customerDashboardViewModel

        this.customerDashboardViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })

        //  this.customerDashboardViewModel?.ratingString?.set(3.5f)


        this.customerDashboardViewModel?.makePaymentEvent?.observe(requireActivity(), Observer {
            if (it) {

                CustomerDashboardViewModel?.isTransactionPopupCalled?.value = true
                findNavController().navigate(
                        R.id.navigation_transaction, bundleOf(
                        Pair("custid", custId),
                        Pair("type", "0"),
                        Pair("custName", custName)
                    )
                )
            }
        })

        this.customerDashboardViewModel?.receivePaymentEvent?.observe(requireActivity(), Observer {
            if (it) {
                CustomerDashboardViewModel?.isTransactionPopupCalled?.value = true
                findNavController().navigate(
                    R.id.navigation_transaction, bundleOf(
                        Pair("custid", custId),
                        Pair("type", "1"),
                        Pair("custName", custName)
                    )
                )
            }
        })

        CustomerDashboardViewModel?.isTransactionPopupCalled?.observe(requireActivity(), Observer {
            if (!it) {
                this.customerDashboardViewModel.makePaymentEvent?.value = false
                this.customerDashboardViewModel.receivePaymentEvent?.value = false
                this.customerDashboardViewModel.fetchCustomerDashboardDetails(custId)
            }
        })


        this.customerDashboardViewModel.customersDataLiveData?.observe(requireActivity(), Observer {
            this.data.clear()
            if (!it.isNullOrEmpty()) {
                this.data = it as ArrayList<Entry>
                loadDataToRCView()
            }
        })

        this.customerDashboardViewModel.errorData.observe(requireActivity(), Observer {

            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

        })
    }


    fun loadDataToRCView() {
        data?.let {
            adapter = Recycler_CustomerView_Adapter(data)
        }

        layoutManager = LinearLayoutManager(activity)
        binding?.root?.recyclerCustomerView?.layoutManager = layoutManager
        binding?.root?.recyclerCustomerView?.adapter = adapter
    }


}