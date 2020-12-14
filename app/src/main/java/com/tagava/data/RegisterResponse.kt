package com.tagava.data

/**
 * Created by Devendra Shewale on 05/12/20.
 */
data class RegisterResponse(
    val `data`: List<String>,
    val error: List<Any>,
    val status: String
)