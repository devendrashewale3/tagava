package com.tagava.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tagava.R
import com.tagava.databinding.TransactionDialogFragmentBinding
import com.tagava.ui.customer_dashboard.CustomerDashboardViewModel
import com.tagava.util.CustomeProgressDialog

class TransactionDialogFragment : DialogFragment() {


    private lateinit var transactionDialogViewModel: TransactionDialogViewModel

    private var binding: TransactionDialogFragmentBinding? = null
    var custId: String? = null
    var type: String? = null
    var custName: String? = null
    var customeProgressDialog: CustomeProgressDialog? = null
    override fun onResume() {
        super.onResume()

        this.transactionDialogViewModel.CustomerPaymentStatus.value = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.transaction_dialog_fragment, container, false
        )
        customeProgressDialog = CustomeProgressDialog(requireContext())

        custId = arguments?.getString("custid")
        type = arguments?.getString("type")
        custName = arguments?.getString("custName")

        initViewModel()
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    fun initViewModel() {

        var transactionDialogViewModelFactory = TransactionDialogViewModelFactory()
        transactionDialogViewModel =
            activity?.let {
                ViewModelProviders.of(it, transactionDialogViewModelFactory)
                    .get(TransactionDialogViewModel::class.java)
            }!!
        binding?.viewmodelTransaction = transactionDialogViewModel

        transactionDialogViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })

        transactionDialogViewModel.customerId?.set(custId)
        transactionDialogViewModel.giveorgot?.set(type)

        transactionDialogViewModel.amount?.set("")

        transactionDialogViewModel.CustomerPaymentStatus.observe(requireActivity(), Observer {
            if (it) {
                lifecycleScope.launchWhenResumed {
                    val bundle = bundleOf(
                        Pair("custid", custId),
                        Pair("custName", custName)
                    )

                    CustomerDashboardViewModel?.isTransactionPopupCalled?.value = false
                    findNavController().popBackStack()

                }
            }
        })

        this.transactionDialogViewModel.errorData.observe(requireActivity(), Observer {

            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

        })
    }

}