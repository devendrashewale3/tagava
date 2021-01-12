package com.tagava.data

data class CreatePaymentResponse(
    val `data`: List<TransactionData>,
    val error: List<Any>,
    val status: String
)