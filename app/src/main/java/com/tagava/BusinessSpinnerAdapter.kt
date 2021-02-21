package com.tagava

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatTextView
import com.tagava.data.BusinessData

class BusinessSpinnerAdapter(context: Context?, var dataSourceList: List<BusinessData>?) :
        BaseAdapter() {
    var dataSource: ArrayList<BusinessData>? = null

    init {
        var footer = BusinessData("footer", "New Business")
        dataSource = dataSourceList as ArrayList<BusinessData>?
        dataSource?.add(footer)
    }

    private val inflater: LayoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.spinner_dropdown_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }


        vh.label.text = dataSource?.get(position)?.businessName

        if (dataSource?.get(position)?.businessId.equals("footer")) {
            vh.label.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_input_add, 0)
        }


        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource?.get(position);
    }

    override fun getCount(): Int {
        return dataSource?.size!!;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: AppCompatTextView

        init {
            label = row?.findViewById(R.id.textSpinner) as AppCompatTextView
        }
    }

}
