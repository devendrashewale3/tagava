package com.tagava.repository

interface IAPICallback<T, V> {
    /*
     * gives callback if get response success
     * */
    fun onResponseSuccess(responseData: Any?)

    /*
     * gives callback if get response failure
     * */
    fun onResponseFailure(failureData: String)
}