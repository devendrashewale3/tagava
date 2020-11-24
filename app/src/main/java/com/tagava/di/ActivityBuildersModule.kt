package com.tagava.di

import com.tagava.AuthActivity
import com.tagava.OTPActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Devendra Shewale on 09/08/20.
 */

@Module
abstract class ActivityBuildersModule {



    @ContributesAndroidInjector
    abstract fun  contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun  contributeOTPActivity(): OTPActivity
}