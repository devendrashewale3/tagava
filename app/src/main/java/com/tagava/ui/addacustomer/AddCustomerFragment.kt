package com.tagava.ui.addacustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tagava.R
import com.tagava.databinding.FragmentAddcustomerBinding
import com.tagava.util.CustomeProgressDialog

class AddCustomerFragment : Fragment() {


    private lateinit var addCustomerViewModel: AddCustomerViewModel
    var binding: FragmentAddcustomerBinding? = null
    var customeProgressDialog: CustomeProgressDialog? = null

    override fun onResume() {
        super.onResume()
        addCustomerViewModel.addCustomerStausStatusLiveData.value = false
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_addcustomer, container, false
        )
        customeProgressDialog = CustomeProgressDialog(requireActivity())


        initViewModel()
        return binding?.root
    }


    private fun initViewModel() {

        var addCustomerViewModelFactory = AddCustomerViewModelFactory()
        addCustomerViewModel =
            activity?.let {
                ViewModelProviders.of(it, addCustomerViewModelFactory)
                    .get(AddCustomerViewModel::class.java)
            }!!
        binding?.viewmodelAddCustomer = addCustomerViewModel

        addCustomerViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })


        addCustomerViewModel.addCustomerStausStatusLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(activity, "Customer added successfully " + it, Toast.LENGTH_SHORT)
                        .show();
                addCustomerViewModel.customerName?.set("")
                addCustomerViewModel.mobileNumber?.set("")
                addCustomerViewModel.addCustomerStausStatusLiveData.value = false
                findNavController().navigate(R.id.navigation_home)
            }


        })

        addCustomerViewModel.errorData.observe(viewLifecycleOwner, Observer {

            if (it.code.equals("CS-INV-MB-003"))
                Toast.makeText(requireContext(), "Customer already added with same number.", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        })
    }


}
