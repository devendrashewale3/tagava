package com.tagava.data

data class CustomerDashboardData(
    val entries: List<Entry>,
    val getOrGiveMsg: String,
    val totalAmount: Int
)