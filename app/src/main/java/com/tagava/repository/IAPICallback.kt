package com.tagava.repository

import com.tagava.data.ErrorData

interface IAPICallback<T, V> {
    /*
     * gives callback if get response success
     * */
    fun onResponseSuccess(responseData: Any?)

    /*
     * gives callback if get response failure
     * */
    fun onResponseFailure(failureData: ErrorData?)
}