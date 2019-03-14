package com.miquido.parsepubsample.tableofcontents

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.miquido.parsepubsample.R

class MetadataAdapter(context: Context, model: Array<Pair<String, String?>>) :
    ArrayAdapter<Pair<String, String?>>(context, R.layout.item_metadata, model) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = inflater.inflate(R.layout.item_metadata, parent, false)
            holder = ViewHolder()
            holder.metadataItem = view.findViewById(R.id.metadataItemText)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val metadataItem = getItem(position)
        metadataItem?.let {
            val rowText = String.format("%s: %s", it.first, it.second.orEmpty())
            val spannable = SpannableString(rowText)
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                it.first.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            holder.metadataItem.text = spannable

        }

        return view
    }

    class ViewHolder {
        lateinit var metadataItem: TextView
    }
}