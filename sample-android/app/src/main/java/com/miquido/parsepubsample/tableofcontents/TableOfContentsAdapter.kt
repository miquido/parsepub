package com.miquido.parsepubsample.tableofcontents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import com.miquido.parsepubsample.R
import kotlinx.android.synthetic.main.item_toc.view.*

class TableOfContentsAdapter(context: Context?) : RecyclerView.Adapter<TableOfContentsAdapter.ViewHolder>() {

    var tocModel: EpubTableOfContentsModel =
        EpubTableOfContentsModel(emptyList())
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClickListener: ((NavigationItemModel) -> Unit)? = null

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(inflater.inflate(R.layout.item_toc, parent, false))

    override fun getItemCount() = tocModel.tableOfContents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(tocModel.tableOfContents[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(model: NavigationItemModel) {
            itemView.tocItemText.text = model.label
            itemView.setOnClickListener { onItemClickListener?.invoke(model) }
        }
    }
}