package com.tagava.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tagava.R
import com.tagava.databinding.ActivityAuthBinding
import com.tagava.util.CustomeProgressDialog
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {


    private val TAG: String = "AuthActivity"

    lateinit var authViewModel: AuthViewModel
    var binding: ActivityAuthBinding? = null
    var customeProgressDialog: CustomeProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        customeProgressDialog = CustomeProgressDialog(this)

        initViewModel()

        register_textView.setOnClickListener {
            var intent = Intent(this@AuthActivity, RegisterActivity::class.java);
            startActivity(intent)
        }
    }

    private fun initViewModel() {

        var authViewModelFactory = AuthViewModelFactory()
        authViewModel =
            ViewModelProviders.of(this@AuthActivity, authViewModelFactory)
                .get(AuthViewModel::class.java)
        binding?.viewmodel = authViewModel

        authViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })


        authViewModel.loginDataLiveData.observe(this, Observer {
           // Toast.makeText(this@AuthActivity, "The OTP is " + it, Toast.LENGTH_SHORT).show();
            if (it) {
                var intent = Intent(this@AuthActivity, OTPActivity::class.java);
                startActivity(intent)
            }
        })

        authViewModel.errorData.observe(this, Observer {

           Toast.makeText(this@AuthActivity,it.message,Toast.LENGTH_LONG).show()

        })

        AuthViewModel.authTokenDataLiveData.observe(this@AuthActivity, Observer {
            val sharedPreference =  getSharedPreferences("TAGAVA_PREFERENCES", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("tkn",it)
            editor.commit()
        })
    }

}
