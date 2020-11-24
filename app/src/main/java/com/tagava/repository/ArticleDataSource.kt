package com.tagava.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource

import com.tagava.data.Article
import com.tagava.data.ArticleItem
import com.tagava.di.APIComponent

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Devendra Shewale on 03/08/20.
 */
class ArticleDataSource : PageKeyedDataSource<Long, ArticleItem>() {
    var TAG = "ArticleDataSource"
    lateinit var apiComponent: APIComponent


    @Inject
    lateinit var retrofit: Retrofit
    lateinit var apiService: APIService

    init {
//        var apiComponent: APIComponent = App.apiComponent
//        apiComponent.inject(this)
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ArticleItem>
    ) {
        apiService = retrofit.create(APIService::class.java)
        var articleList = apiService.fetchArticles(1)
        articleList.enqueue(object : Callback<Article> {
            override fun onFailure(call: Call<Article>, t: Throwable) {
                Log.d(TAG, "error " + t.message)
            }

            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                var articleList = response.body()

                if (articleList != null) {
                    Log.e(
                        TAG,
                        " articleList loadInitial " + "-" + articleList.size + articleList.toString()
                    )
                }
                if (articleList != null) {
                    callback.onResult(articleList.toMutableList(), null, 2)
                }

            }

        })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ArticleItem>) {
        apiService = retrofit.create(APIService::class.java)
        var articleList = apiService.fetchArticles(params.key)
        articleList.enqueue(object : Callback<Article> {
            override fun onFailure(call: Call<Article>, t: Throwable) {
                Log.d(TAG, "error " + t.message)
                Log.e(TAG, " params.key" + params.key)
            }

            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                Log.e(TAG, " params.key" + params.key)

                var articleList = response.body()
                if (articleList != null) {
                    Log.e(
                        TAG,
                        " articleList loadAfter " + "  " + articleList.size + articleList.toString()
                    )
                }
                articleList?.toMutableList()?.let { callback.onResult(it, (params.key) + 1) }

            }

        })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ArticleItem>) {

    }
}


