package com.tagava.ui.dashboard

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tagava.R
import com.tagava.View_Holder
import com.tagava.data.Content
import java.util.*
import kotlin.collections.ArrayList


class Recycler_View_Adapter(
    list: ArrayList<Content>
) :
    RecyclerView.Adapter<View_Holder>() {
    var list = ArrayList<Content>()

    init {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_Holder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return View_Holder(v)
    }

    override fun onBindViewHolder(holder: View_Holder, position: Int) {
        holder.title.setText(list[position].customerName)
        holder.description.setText(list[position].getOrGiveMsg)
        holder.placeholder.text = list[position].customerName.subSequence(0, 1)
        holder.amountTextView.text = list[position].amount.toString()
        holder.amountDescTextView.text = list[position].getOrGiveMsg

        if (list[position].getOrGiveMsg.equals("give"))
            holder.amountTextView.setTextColor(Color.RED)
        else
            holder.amountTextView.setTextColor(Color.GREEN)

        var drawable: GradientDrawable = holder.placeholder.background as GradientDrawable
        var color =
            (Color.argb(255, Random().nextInt(255), Random().nextInt(255), Random().nextInt(255)))
        drawable.setColor(color)
        animate(holder)
        val bundle = bundleOf(
            Pair("custid", list[position].customerId),
            Pair("custName", list[position].customerName)
        )
        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigation_customer_dashboard, bundle)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    // Insert a new item to the RecyclerView
    fun insert(position: Int, data: Content) {
        list.add(position, data)
        notifyItemInserted(position)
    }

    // Remove a RecyclerView item containing the Data object
    fun remove(data: Content) {
        val position = list.indexOf(data)
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun animate(viewHolder: ViewHolder) {
        val animAnticipateOvershoot: Animation = AnimationUtils.loadAnimation(
            viewHolder.itemView.context,
            R.anim.anticipate_overshoot_interpolator
        )
        viewHolder.itemView.animation = animAnticipateOvershoot
    }


}