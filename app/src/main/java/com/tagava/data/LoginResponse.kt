package com.tagava.data

/**
 * Created by Devendra Shewale on 05/12/20.
 */
data class LoginResponse(
    val `data`: List<Data>,
    val error: List<ErrorData>,
    val status: String
)