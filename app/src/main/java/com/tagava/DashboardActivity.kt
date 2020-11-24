package com.tagava


import android.os.Bundle
import dagger.android.DaggerActivity

class DashboardActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
}
