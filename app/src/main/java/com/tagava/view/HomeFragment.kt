package com.tagava.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tagava.R
import com.tagava.data.Article
import com.tagava.data.ArticleItem

import com.tagava.databinding.ArticleListLayoutBinding

import com.tagava.viewmodel.HomeViewModel
import com.tagava.viewmodel.HomeViewModelFactory


class HomeFragment : Fragment() {

    lateinit var homeViewModel: HomeViewModel
    private var articleListLayoutBinding: ArticleListLayoutBinding? = null
    var fragmentHomeView: View? = null
    private var listAdapter: ArticleListAdapter? = null
    private var pagesListAdapter: ArticlePagedListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articleListLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.article_list_layout, container, false)

        fragmentHomeView = articleListLayoutBinding?.root

        //initAdapter()

        //  fetchBlogAriticles()

        initPageListAdapter()

        fetchPagedListBlogArticles()
        return fragmentHomeView
    }


    private fun initViewModel() {
        var homeViewModelFactory = HomeViewModelFactory()
        homeViewModel =
            ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    fun fetchBlogAriticles() {
        homeViewModel.articleLiveData.observe(this, object : Observer<Article> {
            override fun onChanged(articleList: Article?) {
                articleList?.apply {
                    println(articleList.toString())
                    listAdapter?.setAdapterList(articleList)

                }

            }
        })
    }

    private fun fetchPagedListBlogArticles() {
        homeViewModel.fetchPagedListLiveData()
            ?.observe(this@HomeFragment, object : Observer<PagedList<ArticleItem>> {
                override fun onChanged(t: PagedList<ArticleItem>?) {
                    Log.d("HomeFragment", "articleList******* " + t.toString())

                    pagesListAdapter?.submitList(t)
                }
            })

    }



    private fun initAdapter() {

        listAdapter = ArticleListAdapter(this@HomeFragment.requireActivity())

        articleListLayoutBinding?.articleList?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = listAdapter
        }
    }

    private fun initPageListAdapter() {
        pagesListAdapter = ArticlePagedListAdapter(this@HomeFragment.requireContext())

        articleListLayoutBinding?.articleList?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = pagesListAdapter
        }
    }


}
