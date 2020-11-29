package com.tagava

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import dagger.android.DaggerActivity
import kotlinx.android.synthetic.main.activity_otp.*

class OTPActivity : DaggerActivity() {
     var  otpTextView: OtpTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        otpTextView = findViewById(R.id.otp_view)
        otpTextView?.requestFocusOTP()

        otpTextView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                otpTextView?.resetState()
            }

            override fun onOTPComplete(otp: String) {
                Toast.makeText(this@OTPActivity, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
            }
        }

        verify_button.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}
