package com.tagava.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tagava.repository.RetrofitRepository

/**
 * Created by Devendra Shewale on 07/12/20.
 */
class RegisterViewModelFactory : ViewModelProvider.Factory {

    var retrofitRepository = RetrofitRepository()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}