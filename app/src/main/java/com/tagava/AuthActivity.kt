package com.tagava

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.RequestManager
import dagger.android.DaggerActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerActivity() {


    private val TAG: String = "AuthActivity"

    @Inject
    lateinit var testStr:String
    @Inject
    lateinit var app:String

    @Inject
    lateinit var logo: Drawable

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

       Log.d(TAG,"--" + testStr +" --"+ app)
        setLogo()
    }

    fun setLogo(){
        requestManager
            .load(logo)
            .into(login_logo)
    }
}
