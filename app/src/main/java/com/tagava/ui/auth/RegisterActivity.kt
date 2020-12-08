package com.tagava.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tagava.DashboardActivity
import com.tagava.R
import com.tagava.databinding.ActivityRegisterBinding
import com.tagava.util.CustomeProgressDialog


class RegisterActivity : AppCompatActivity() {

    lateinit var registerViewModel: RegisterViewModel
    var binding: ActivityRegisterBinding? = null
    var customeProgressDialog: CustomeProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Register"
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        customeProgressDialog = CustomeProgressDialog(this)

        initViewModel()


    }

    private fun initViewModel() {

        var registerViewModelFactory = RegisterViewModelFactory()
        registerViewModel =
            ViewModelProviders.of(this@RegisterActivity, registerViewModelFactory)
                .get(RegisterViewModel::class.java)
        binding?.viewmodelRegister = registerViewModel

        registerViewModel?.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })


        registerViewModel.registrationStatusLiveData.observe(this, Observer {
            Toast.makeText(this@RegisterActivity, "The status is " + it, Toast.LENGTH_SHORT).show();
            if (it) {
                var intent = Intent(this@RegisterActivity, DashboardActivity::class.java);
                startActivity(intent)
            }
        })
    }
}