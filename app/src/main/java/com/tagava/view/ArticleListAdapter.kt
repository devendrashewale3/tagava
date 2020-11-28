package com.tagava.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tagava.BR
import com.tagava.R
import com.tagava.data.Article
import com.tagava.data.ArticleItem
import com.tagava.databinding.ArticleListItemBinding


/**
 * Created by Devendra Shewale on 30/07/20.
 */
class ArticleListAdapter(var context: Context) :
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    private var list: ArrayList<ArticleItem> = ArrayList<ArticleItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ArticleListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.article_list_item, parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Log.d("ADapter","Info:::"+list.get(position).content)
        holder.bind(list.get(position))
    }

    fun setAdapterList(list: Article) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Any) {
            binding.setVariable(BR.articlemodel, article)
            binding.executePendingBindings()
        }

    }

}