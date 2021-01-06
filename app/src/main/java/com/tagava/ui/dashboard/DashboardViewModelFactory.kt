package com.tagava.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tagava.repository.RetrofitRepository

/**
 * Created by Devendra Shewale on 07/12/20.
 */
class DashboardViewModelFactory : ViewModelProvider.Factory {

    var retrofitRepository = RetrofitRepository()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}