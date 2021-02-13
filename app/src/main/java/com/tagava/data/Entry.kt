package com.tagava.data

data class Entry(
        val balance: Int,
        val date: String,
        val gaveOrGot: String,
        val gaveOrGotAmount: Int,
        val transactionId: String,
        val rating: String
)