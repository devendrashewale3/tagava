package com.tagava.data

/**
 * Created by Devendra Shewale on 05/12/20.
 */
data class DashboardResponse(
    val `data`: List<DashboardData>,
    val error: List<ErrorData>,
    val status: String
)