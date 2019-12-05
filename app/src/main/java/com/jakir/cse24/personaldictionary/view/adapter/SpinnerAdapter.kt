package com.jakir.cse24.personaldictionary.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

class SpinnerAdapter(
    context: Context, @LayoutRes private val layoutResource: Int,
    private val types: List<String>
) : ArrayAdapter<String>(context, layoutResource, types) {

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = super.getDropDownView(position, convertView, parent)
        val textView: TextView = view as TextView
        if (position == 0) {
            textView.setTextColor(Color.GRAY)
        }
        return view
    }
}