package com.tagava

import com.tagava.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by Devendra Shewale on 09/08/20.
 */
class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {

    return DaggerAppComponent.builder().application(this).build()
    }
}