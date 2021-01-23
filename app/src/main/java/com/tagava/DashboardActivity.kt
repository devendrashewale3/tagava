package com.tagava




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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tagava.data.BusinessData
import com.tagava.ui.auth.AuthViewModel


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(resources.getColor(R.color.colorApp))
        }


        var category = resources.getStringArray(R.array.category)

        val toolbar: Toolbar = findViewById<View>(R.id.toolBar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        //toolbar.setLogo(R.drawable.ic_drawer)


        var buisnessData: List<BusinessData>? = AuthViewModel.businessIDDataLiveData?.value


        val spinnerAdapter = BusinessSpinnerAdapter(applicationContext, buisnessData)
        val navigationSpinner = findViewById<Spinner>(R.id.spinner_nav)
        navigationSpinner.adapter = spinnerAdapter
        navigationSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@DashboardActivity,
                    "you selected: " + category.get(position),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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

    }


}
