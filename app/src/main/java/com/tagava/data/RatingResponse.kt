package com.tagava.data

data class RatingResponse(
        val `data`: List<String>,
        val error: List<Any>,
        val status: String
)