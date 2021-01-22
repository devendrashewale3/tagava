package com.tagava.ui.auth

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tagava.DashboardActivity
import com.tagava.R
import com.tagava.databinding.ActivityOtpBinding
import com.tagava.util.CustomeProgressDialog

class OTPActivity : AppCompatActivity() {
     var  otpTextView: OtpTextView? = null

    lateinit var authViewModel: AuthViewModel
    var binding: ActivityOtpBinding? = null
    var customeProgressDialog: CustomeProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)

        customeProgressDialog = CustomeProgressDialog(this)

        otpTextView = findViewById(R.id.otp_view)
        otpTextView?.requestFocusOTP()

        otpTextView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                otpTextView?.resetState()
            }

            override fun onOTPComplete(otp: String) {
                //  Toast.makeText(this@OTPActivity, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
                authViewModel.otpText?.set(otp)
            }
        }

        initViewModel()


    }

    private fun initViewModel() {
        var authViewModelFactory = AuthViewModelFactory()
        authViewModel =
            ViewModelProviders.of(this@OTPActivity, authViewModelFactory)
                .get(AuthViewModel::class.java)

        binding?.viewmodelOTP = authViewModel

        authViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })

        authViewModel.verifyOTPStatusLiveData.observe(this, Observer {
            if (it) {
                var intent = Intent(this@OTPActivity, RegisterActivity::class.java);
                startActivity(intent)
            }
        })

        authViewModel.verifyLoginOTPStatusLiveData.observe(this, Observer {
            if (it) {

                authViewModel.fetchAllBusiness()
            }
        })

        AuthViewModel.businessSelectedIDDataLiveData.observe(this, Observer {
            val sharedPreference =  getSharedPreferences("TAGAVA_PREFERENCES", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("bid",it)
            editor.commit()
        })

        authViewModel.businessDataFetchStatus.observe(this, Observer {
            if (it) {
                var intent = Intent(this@OTPActivity, DashboardActivity::class.java);
                startActivity(intent)
            }
        })

        authViewModel.errorData.observe(this, Observer {

            Toast.makeText(this@OTPActivity,it.message, Toast.LENGTH_LONG).show()

        })

        AuthViewModel.authTokenDataLiveData.observe(this@OTPActivity, Observer {
            val sharedPreference =  getSharedPreferences("TAGAVA_PREFERENCES", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("tkn",it)
            editor.commit()
        })

    }
}
