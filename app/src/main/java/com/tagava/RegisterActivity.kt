package com.tagava

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tagava.ui.auth.OTPActivity
import kotlinx.android.synthetic.main.activity_login.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Register"
        setContentView(R.layout.activity_login)



        register_button.setOnClickListener {
            var intent = Intent(this@RegisterActivity, OTPActivity::class.java);
            startActivity(intent)
        }
    }
}