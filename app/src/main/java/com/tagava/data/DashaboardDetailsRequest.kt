package com.tagava.data

/**
 * Created by Devendra Shewale on 05/01/21.
 */
data class DashaboardDetailsRequest(
        var businessId: String,
        var searchByNameOrMobile: String,
        var filterBy: String,
        var customerId: String

)