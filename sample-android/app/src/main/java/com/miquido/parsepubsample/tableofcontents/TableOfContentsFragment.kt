package com.miquido.parsepubsample.tableofcontents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepubsample.EpubBookProcessor
import com.miquido.parsepubsample.R
import kotlinx.android.synthetic.main.fragment_table_of_contents.*

class TableOfContentsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_table_of_contents, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val epubProcessor = activity as? EpubBookProcessor
        epubProcessor?.let {
            it.parseEpubBook {
                activity?.runOnUiThread { showTableOfContents(it?.epubTableOfContentsModel) }
            }
        }
    }

    private fun showTableOfContents(tocModel: EpubTableOfContentsModel?) {
        val tocAdapter = TableOfContentsAdapter(activity)
        with(tocRecycler) {
            layoutManager = LinearLayoutManager(activity)
            adapter = tocAdapter
        }
        tocModel?.let { tocAdapter.tocModel = it }
    }
}

