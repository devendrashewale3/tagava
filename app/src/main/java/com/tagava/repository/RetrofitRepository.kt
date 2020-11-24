package com.tagava.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import com.tagava.App
import com.tagava.data.Article
import com.tagava.di.APIComponent

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitRepository {
    var TAG = "RetrofitRepository"
    lateinit var apiComponent: APIComponent

    var articleMutableList: MutableLiveData<Article> = MutableLiveData()

    @Inject
    lateinit var retrofit: Retrofit

    init {
//        var apiComponent: APIComponent = App.apiComponent
//        apiComponent.inject(this)
    }

    fun fetchArticleList(): LiveData<Article> {

        var apiService: APIService = retrofit.create(APIService::class.java)
        var articleList: Call<Article> = apiService.fetchArticles(1)
        articleList.enqueue(object : Callback<Article> {
            override fun onFailure(call: Call<Article>, t: Throwable) {
                Log.d(TAG, "error " + t.message)
            }

            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                var articleList = response.body()
                articleMutableList.value = articleList
            }
        })

        return articleMutableList
    }

}
