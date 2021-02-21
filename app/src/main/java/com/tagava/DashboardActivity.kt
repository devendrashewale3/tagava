package com.tagava




import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tagava.data.BusinessData
import com.tagava.ui.auth.AuthViewModel
import com.tagava.ui.auth.RegisterActivity
import com.tagava.ui.dashboard.DashboardViewModel
import com.tagava.ui.dashboard.DashboardViewModelFactory


class DashboardActivity : AppCompatActivity() {

    private lateinit var dashboardViewModel: DashboardViewModel

    companion object {
        var flag = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(resources.getColor(R.color.colorApp))
        }



        val toolbar: Toolbar = findViewById<View>(R.id.toolBar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        //toolbar.setLogo(R.drawable.ic_drawer)

        initViewModel()
        var buisnessData: List<BusinessData>? = AuthViewModel.businessIDDataLiveData?.value


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_addcustomer
            )
        )
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_customer_dashboard ->
                    navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE

            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {

            navController.popBackStack()

        }

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))

        toolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"),
                PorterDuff.Mode.SRC_ATOP
        );

        if (!buisnessData.isNullOrEmpty() && buisnessData.size > 0) {
            val spinnerAdapter = BusinessSpinnerAdapter(applicationContext, buisnessData)
            val navigationSpinner = findViewById<Spinner>(R.id.spinner_nav)

            navigationSpinner.adapter = spinnerAdapter

            buisnessData.forEachIndexed { index, element ->
                if (AuthViewModel.businessSelectedIDDataLiveData.value?.equals(element.businessId)!!) {
                    navigationSpinner.setSelection(index)
                    flag = true
                }
            }
            navigationSpinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View,
                        position: Int,
                        id: Long
                ) {

                    if (flag) {
                        val selectedData: BusinessData = parent?.getItemAtPosition(position) as BusinessData
                        if (selectedData.businessId.equals("footer")) {
                            finish()
                            var intent = Intent(this@DashboardActivity, RegisterActivity::class.java);
                            startActivity(intent)
                        } else {
                            AuthViewModel.businessSelectedIDDataLiveData.value = selectedData.businessId

                            val sharedPreference = getSharedPreferences("TAGAVA_PREFERENCES", Context.MODE_PRIVATE)
                            var editor = sharedPreference.edit()
                            editor.putString("bid", selectedData.businessId)
                            editor.commit()
                            dashboardViewModel.fetchDashboardDetails()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }


        }

    }

    private fun initViewModel() {

        var dashboardViewModelFactory = DashboardViewModelFactory()
        this.dashboardViewModel = ViewModelProviders.of(this, dashboardViewModelFactory)
                .get(DashboardViewModel::class.java)

    }


}
