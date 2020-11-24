package com.tagava.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tagava.data.Article
import com.tagava.data.ArticleItem
import com.tagava.repository.ArticleDataSource
import com.tagava.repository.ArticleDataSourceFactory
import com.tagava.repository.RetrofitRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Devendra Shewale on 30/07/20.
 */
class HomeViewModel(retrofitRepository: RetrofitRepository) : ViewModel() {

    var retrofitRepository: RetrofitRepository
    var articleLiveData: LiveData<Article> = MutableLiveData()


    var articleDataSourceFactory: ArticleDataSourceFactory? = null
    var dataSourceMutableLiveData: MutableLiveData<ArticleDataSource>? = null
    var executor: Executor? = null
    var pagedListLiveData: LiveData<PagedList<ArticleItem>>? = null

    init {
        this.retrofitRepository = retrofitRepository
        fetchArticleFromRepository()

        articleDataSourceFactory = ArticleDataSourceFactory()
        dataSourceMutableLiveData = articleDataSourceFactory?.getMutableLiveData()

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(5)
            .build()

        executor = Executors.newFixedThreadPool(5)

        executor?.let {

            articleDataSourceFactory?.let { articleDataSourceFactory ->
                pagedListLiveData =
                    LivePagedListBuilder(articleDataSourceFactory, config)
                        .setFetchExecutor(it)
                        .build()
            }
        }



    }

    fun fetchArticleFromRepository() {
        articleLiveData = retrofitRepository.fetchArticleList()
    }

    fun fetchPagedListLiveData(): LiveData<PagedList<ArticleItem>>? {
        return pagedListLiveData
    }

}