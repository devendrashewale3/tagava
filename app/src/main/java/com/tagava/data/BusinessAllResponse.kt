package com.tagava.data

/**
 * Created by Devendra Shewale on 11/01/21.
 */
data class BusinessAllResponse(
    val `data`: List<BusinessData>,
    val error: List<ErrorData>,
    val status: String
)