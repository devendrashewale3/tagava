package com.tagava.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
//import com.tagava.App
import com.tagava.data.ArticleItem
import com.tagava.di.APIComponent
import javax.inject.Inject

/**
 * Created by Devendra Shewale on 03/08/20.
 */
class ArticleDataSourceFactory : DataSource.Factory<Long, ArticleItem>() {

    @Inject
    lateinit var articleDataSource: ArticleDataSource
    var articleMutableList: MutableLiveData<ArticleDataSource> = MutableLiveData()

    init {
//        var apiComponent: APIComponent = App.apiComponent
//        apiComponent.inject(this)
    }

    override fun create(): DataSource<Long, ArticleItem> {
        articleDataSource = ArticleDataSource()

        Log.d("DataSourceFactory", "articleList " + articleDataSource)
        articleMutableList.postValue(articleDataSource)

        return articleDataSource
    }

    fun getMutableLiveData(): MutableLiveData<ArticleDataSource> {
        return articleMutableList
    }
}