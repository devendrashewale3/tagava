package com.tagava


import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*
import kotlin.collections.ArrayList


class Recycler_View_Adapter(list: ArrayList<Data>) :
    RecyclerView.Adapter<View_Holder>() {
    var list = ArrayList<Data>()

    init {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_Holder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return View_Holder(v)
    }

    override fun onBindViewHolder(holder: View_Holder, position: Int) {
        holder.title.setText(list[position].title)
        holder.description.setText(list[position].description)
        holder.placeholder.text = list[position].initials
        holder.amountTextView.text = list[position].amount
        holder.amountDescTextView.text = list[position].amountDes

        if (list[position].returnBack)
            holder.amountTextView.setTextColor(Color.RED)
        else
            holder.amountTextView.setTextColor(Color.GREEN)

        var drawable: GradientDrawable = holder.placeholder.background as GradientDrawable
        var color =
            (Color.argb(255, Random().nextInt(255), Random().nextInt(255), Random().nextInt(255)))
        drawable.setColor(color)
        animate(holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    // Insert a new item to the RecyclerView
    fun insert(position: Int, data: Data) {
        list.add(position, data)
        notifyItemInserted(position)
    }

    // Remove a RecyclerView item containing the Data object
    fun remove(data: Data) {
        val position = list.indexOf(data)
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun animate(viewHolder: ViewHolder) {
        val animAnticipateOvershoot: Animation =
            AnimationUtils.loadAnimation(viewHolder.itemView.context, R.anim.anticipate_overshoot_interpolator)
        viewHolder.itemView.animation = animAnticipateOvershoot
    }


}