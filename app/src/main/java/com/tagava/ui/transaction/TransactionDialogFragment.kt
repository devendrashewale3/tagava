package com.tagava.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tagava.R
import com.tagava.databinding.TransactionDialogFragmentBinding
import com.tagava.util.CustomeProgressDialog

class TransactionDialogFragment : DialogFragment() {


    private lateinit var transactionDialogViewModel: TransactionDialogViewModel

    private var binding: TransactionDialogFragmentBinding? = null
    var custId: String? = null
    var type: String? = null
    var customeProgressDialog: CustomeProgressDialog? = null


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

        transactionDialogViewModel.CustomerPaymentStatus.value = false

        transactionDialogViewModel.CustomerPaymentStatus.observe( requireActivity(), Observer {
            if (it){
                findNavController().popBackStack()
            }
        })
    }

}