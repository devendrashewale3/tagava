package com.tagava


import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


//The adapters View Holder
class View_Holder internal constructor(itemView: View) : ViewHolder(itemView) {
    var cv: CardView
    var title: TextView
    var description: TextView
    var placeholder: TextView
    var initialsLayout: FrameLayout
    var amountTextView : TextView
    var amountDescTextView : TextView

    init {
        cv = itemView.findViewById(R.id.cardView)
        title = itemView.findViewById(R.id.title)
        description = itemView.findViewById(R.id.description)
        placeholder = itemView.findViewById(R.id.placeholder) as TextView
        initialsLayout = itemView.findViewById(R.id.initialsLayout) as FrameLayout
        amountTextView = itemView.findViewById(R.id.amount) as TextView
        amountDescTextView = itemView.findViewById(R.id.amountDesc) as TextView


    }
}