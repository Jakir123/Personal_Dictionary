package com.jakir.cse24.personaldictionary.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.toolbar_view.view.*


class ToolbarView : LinearLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    fun bindTo(title: String?, subTitle: String?) {
        hideOrSetText(tvTitle, title)
        hideOrSetText(tvSubTitle, subTitle)
    }

    private fun hideOrSetText(tv: TextView, text: String?) {
        if (text == null || text == "") tv.visibility = View.GONE else tv.text = text
    }

}