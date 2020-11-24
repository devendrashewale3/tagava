package com.tagava.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tagava.BR
import com.tagava.R
import com.tagava.data.ArticleItem
import com.tagava.databinding.ArticleListItemBinding

/**
 * Created by Devendra Shewale on 03/08/20.
 */
class ArticlePagedListAdapter(var context: Context) :
    PagedListAdapter<ArticleItem, ArticlePagedListAdapter.ViewHolder>(
        ArticleItem.CALLBACK
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ArticleListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.article_list_item, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Any) {
            binding.setVariable(BR.articlemodel, article)
            binding.executePendingBindings()
        }

    }
}