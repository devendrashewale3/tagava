package com.tagava.ui.customer_dashboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.tagava.CustomerView_Holder
import com.tagava.R
import com.tagava.data.Entry


class Recycler_CustomerView_Adapter(
    list: ArrayList<Entry>
) :
    RecyclerView.Adapter<CustomerView_Holder>() {
    var list = ArrayList<Entry>()

    init {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerView_Holder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_customer_layout, parent, false)
        return CustomerView_Holder(v)
    }

    override fun onBindViewHolder(holder: CustomerView_Holder, position: Int) {
        holder.title.setText(list[position].date)
        holder.description.setText(list[position].gaveOrGot)
        //   holder.placeholder.text = list[position].initials
        holder.amountTextView.text = list[position].gaveOrGotAmount.toString()
        if (list[position].gaveOrGot.equals("GAVE"))
            holder.amountTextView.setTextColor(Color.RED)
        else
            holder.amountTextView.setTextColor(Color.GREEN)

//        var drawable: GradientDrawable = holder.placeholder.background as GradientDrawable
//        var color =
//            (Color.argb(255, Random().nextInt(255), Random().nextInt(255), Random().nextInt(255)))
//        drawable.setColor(color)
        //      animate(holder)
//        holder.itemView.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.navigation_customer_dashboard)
//        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun animate(viewHolder: CustomerView_Holder) {
        val animAnticipateOvershoot: Animation = AnimationUtils.loadAnimation(
            viewHolder.itemView.context,
            R.anim.anticipate_overshoot_interpolator
        )
        viewHolder.itemView.animation = animAnticipateOvershoot
    }


}