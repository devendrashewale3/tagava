package com.tagava.data

data class CustomerDashboardResponse(
    val `data`: List<CustomerDashboardData>,
    val error: List<ErrorData>,
    val status: String
)