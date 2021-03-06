package com.tagava.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tagava.R
import com.tagava.data.Content
import com.tagava.databinding.FragmentDashboardBinding
import com.tagava.ui.auth.AuthViewModel
import com.tagava.util.CustomeProgressDialog
import kotlinx.android.synthetic.main.fragment_dashboard.view.*


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: Recycler_View_Adapter
    var binding: FragmentDashboardBinding? = null
    var customeProgressDialog: CustomeProgressDialog? = null
    lateinit var data: ArrayList<Content>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

    }

    override fun onResume() {
        super.onResume()
        val navigationSpinner = activity?.findViewById<Spinner>(R.id.spinner_nav)
        navigationSpinner?.visibility = View.VISIBLE

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dashboardViewModel =
//            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        //  val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_dashboard, container, false
        )

        binding?.root?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //Log.e("onQueryTextChange", "called");
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                dashboardViewModel.customerName?.set(query)

                dashboardViewModel.fetchDashboardDetails()
                // Do your task here
                return false
            }
        })
        initViewModel()
        return binding?.root
    }


    private fun initViewModel() {

        var dashboardViewModelFactory = DashboardViewModelFactory()
        this.dashboardViewModel =
                activity?.let {
                    ViewModelProviders.of(it, dashboardViewModelFactory)
                            .get(DashboardViewModel::class.java)
                }!!
        binding?.viewmodelDashboard = this.dashboardViewModel

        this.dashboardViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })

        val sharedPreference = activity?.getSharedPreferences("TAGAVA_PREFERENCES", Context.MODE_PRIVATE)
        var bid: String? = sharedPreference?.getString("bid", "")
        if (!bid.isNullOrEmpty()) {
            bid?.let {
                AuthViewModel.businessSelectedIDDataLiveData.value = it
            }
        }
        this.dashboardViewModel.fetchDashboardDetails()

        this.dashboardViewModel?.customersDataLiveData.observe(requireActivity(), Observer {
            this.data = it as ArrayList<Content>
            loadDataToRCView()
        })

//        this.dashboardViewModel.fetchDashboardDetailsStatusLiveData.observe(
//            requireActivity(),
//            Observer {
//
//                if(it) {
//                    Toast.makeText(
//                        activity,
//                        "Dashboard data called successfully " + it,
//                        Toast.LENGTH_SHORT
//                    )
//                        .show();
//                    // findNavController().navigate(R.id.navigation_home)
//
//                }
//            })
    }

    fun loadDataToRCView() {
        data?.let {
            adapter = Recycler_View_Adapter(data)
        }

        layoutManager = LinearLayoutManager(activity)
        binding?.root?.recyclerview?.layoutManager = layoutManager
        binding?.root?.recyclerview?.adapter = adapter
    }


}
