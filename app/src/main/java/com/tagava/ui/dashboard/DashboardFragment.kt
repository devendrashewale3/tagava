package com.tagava.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tagava.R
import com.tagava.data.Content
import com.tagava.databinding.FragmentDashboardBinding
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
        this.dashboardViewModel.fetchDashboardDetails()

        this.dashboardViewModel?.customersDataLiveData.observe(requireActivity(), Observer {
            this.data = it as ArrayList<Content>
            loadDataToRCView()
        })

        this.dashboardViewModel.fetchDashboardDetailsStatusLiveData.observe(
            requireActivity(),
            Observer {
                Toast.makeText(
                    activity,
                    "Dashboard data called successfully " + it,
                    Toast.LENGTH_SHORT
                )
                    .show();
                // findNavController().navigate(R.id.navigation_home)


            })
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
