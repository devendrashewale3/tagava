package com.tagava


import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


//The adapters View Holder
class CustomerView_Holder internal constructor(itemView: View) : ViewHolder(itemView) {
    var cv: CardView
    var title: TextView
    var description: TextView
    var amountTextView: TextView

    init {
        cv = itemView.findViewById(R.id.cardView)
        title = itemView.findViewById(R.id.title)
        description = itemView.findViewById(R.id.description)
        amountTextView = itemView.findViewById(R.id.amount) as TextView


    }
}