package com.tagava.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tagava.BaseApplication
import com.tagava.di.APIComponent
import com.tagava.repository.RetrofitRepository
import javax.inject.Inject

/**
 * Created by Devendra Shewale on 30/07/20.
 */
class HomeViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

//        var apiComponent: APIComponent = BaseApplication.apiComponent
//        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}