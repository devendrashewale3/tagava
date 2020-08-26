package com.tagava.di

import android.app.Application
import com.tagava.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * Created by Devendra Shewale on 09/08/20.
 */

@Component ( modules = [AndroidInjectionModule::class,
                        ActivityBuildersModule::class,
                        AppModule:: class
            ])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{
        @BindsInstance
         fun application(application: Application): Builder
         fun  build(): AppComponent
    }

}