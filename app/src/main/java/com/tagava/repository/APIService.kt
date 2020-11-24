package com.tagava.repository

import com.tagava.data.Article

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("jet2/api/v1/blogs")
    fun fetchArticles(@Query("page") page: Long, @Query("limit") limit: Long = 10): Call<Article>

}
