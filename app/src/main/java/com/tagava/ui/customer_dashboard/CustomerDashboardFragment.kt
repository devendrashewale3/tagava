package com.tagava.ui.customer_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tagava.Data
import com.tagava.R
import com.tagava.Recycler_CustomerView_Adapter
import com.tagava.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_customer_dashboard.view.*


/**
 * Created by Devendra Shewale on 23/12/20.
 */
class CustomerDashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: Recycler_CustomerView_Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_customer_dashboard, container, false)


        var data: ArrayList<Data>? = fill_with_data()

        data?.let {
            adapter = Recycler_CustomerView_Adapter(data)
        }

        layoutManager = LinearLayoutManager(activity)
        root.recyclerCustomerView?.layoutManager = layoutManager
        root.recyclerCustomerView?.adapter = adapter

        return root

    }

    //Create a list of Data objects
    fun fill_with_data(): ArrayList<Data>? {
        val data: ArrayList<Data> = ArrayList()
        data.add(
            Data(
                "01-Sep-2020 13:20 pm",
                "Bal Rs 350",
                "RS",
                "Rs 2000",
                "You'll get",
                true

            )
        )
        data.add(
            Data(
                "01-Aug-2020 16:20 pm",
                "Bal Rs 150",
                "AS",
                "Rs 1550",
                "You'll get",
                true
            )
        )
        data.add(
            Data(
                "23-July-2020 18:10 pm",
                "Bal Rs 250",
                "MS",
                "Rs 2000",
                "You'll give",
                false
            )
        )




        return data
    }
}