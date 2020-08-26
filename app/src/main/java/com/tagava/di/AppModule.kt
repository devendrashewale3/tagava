package com.tagava.di

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.tagava.R
import dagger.Module
import dagger.Provides

/**
 * Created by Devendra Shewale on 09/08/20.
 */

@Module
object AppModule {


//        @JvmStatic
//        @Provides
//        fun someString():String {
//            return "test"
//        }

    @JvmStatic
    @Provides
    fun  getApp(application: Application):String{
        return  application.toString()
    }

    @JvmStatic
    @Provides
    fun providesRequestOption() :RequestOptions {
        return  RequestOptions
            .placeholderOf(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
    }

    @JvmStatic
    @Provides
    fun providesGlideInstance(application: Application,requestOptions: RequestOptions):RequestManager{
      return Glide.with(application)
          .setDefaultRequestOptions(requestOptions)
    }

    @JvmStatic
    @Provides
    fun providesAppDrawable(application: Application): Drawable {
      return ContextCompat.getDrawable(application,R.drawable.logo)!!
    }
}