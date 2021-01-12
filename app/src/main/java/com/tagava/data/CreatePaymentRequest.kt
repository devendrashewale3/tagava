package com.tagava.data

/**
 * Created by Devendra Shewale on 12/01/21.
 */
data class CreatePaymentRequest(
    var amount: Int,
    var businessId: String,
    var customerId: String,
    var gaveOrGot: String
)