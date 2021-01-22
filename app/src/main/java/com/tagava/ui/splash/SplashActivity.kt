package com.tagava.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tagava.DashboardActivity
import com.tagava.R
import com.tagava.databinding.ActivitySplashBinding
import com.tagava.ui.auth.AuthActivity
import com.tagava.ui.auth.AuthViewModel
import com.tagava.util.CustomeProgressDialog


class SplashActivity : AppCompatActivity() {


    private val TAG: String = "SplashActivity"

    lateinit var splashViewModel: SplashViewModel
    var binding: ActivitySplashBinding? = null
    var customeProgressDialog: CustomeProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        //   customeProgressDialog = CustomeProgressDialog(this)

        initViewModel()


    }

    private fun initViewModel() {

        var authViewModelFactory = SplashViewModelFactory()
        splashViewModel =
            ViewModelProviders.of(this@SplashActivity, authViewModelFactory)
                .get(SplashViewModel::class.java)
        binding?.viewmodelSplash = splashViewModel

        splashViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })


//        splashViewModel.loginDataLiveData.observe(this, Observer {
//            Toast.makeText(this@SplashActivity, "The OTP is " + it, Toast.LENGTH_SHORT).show();
//            if (it) {
//                var intent = Intent(this@SplashActivity, DashboardActivity::class.java);
//                startActivity(intent)
//            }
//        })

        splashViewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashViewModel.SplashState.DashboardActivity -> {
                    goToMainActivity()
                }
                is SplashViewModel.SplashState.AuthActivity -> {
                    goToAuthActivity()
                }
            }
        })

        val sharedPreference =  getSharedPreferences("TAGAVA_PREFERENCES", Context.MODE_PRIVATE)
        var token: String? = sharedPreference.getString("tkn","")
        var bid: String? = sharedPreference.getString("bid","")
        if (!token.isNullOrEmpty()){
             token.let {
                 AuthViewModel.authTokenDataLiveData.value = it
             }
             bid?.let {
                 AuthViewModel.businessSelectedIDDataLiveData.value =it
            }
            goToMainActivity()
        } else
           splashViewModel.fetchRegResponse()

    }

    private fun goToMainActivity() {
        finish()
        startActivity(Intent(this, DashboardActivity::class.java))
    }

    private fun goToAuthActivity() {
        finish()
        startActivity(Intent(this, AuthActivity::class.java))
    }
}
