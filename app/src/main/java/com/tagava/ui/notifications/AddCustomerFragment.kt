package com.tagava.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tagava.R

class AddCustomerFragment : Fragment() {

    private lateinit var addCustomerViewModel: AddCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCustomerViewModel =
            ViewModelProviders.of(this).get(AddCustomerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_addcustomer, container, false)
        // val textView: TextView = root.findViewById(R.id.text_notifications)
        addCustomerViewModel.text.observe(viewLifecycleOwner, Observer {
            // textView.text = it
        })
        return root
    }
}
