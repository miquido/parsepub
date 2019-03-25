package com.miquido.parsepubsample.tableofcontents

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miquido.parsepub.epubparser.EpubParser
import com.miquido.parsepub.model.EpubBook
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepubsample.R
import com.miquido.parsepubsample.copyFileFromAssets
import com.miquido.parsepubsample.openFileInWebView
import kotlinx.android.synthetic.main.activity_toc.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val epubParser = EpubParser()
    private var epubBook: EpubBook? = null
    private var decompressedEpubpath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toc)

        parseEpubBook {
            runOnUiThread {
                progressBar.visibility = View.GONE
                showTableOfContents(it?.epubTableOfContentsModel)
            }
        }
        setListeners()
        setMissingAttributesLogger()
    }

    private fun parseEpubBook(onComplete: (result: EpubBook?) -> Unit) {
        Thread {
            val epubFilePath = copyFileFromAssets(EPUB_BOOK_NAME, cacheDir.path)
            val pathToDecompress = "$filesDir${File.separator}$DIR_EPUB_DECOMPRESSED"
            epubBook = epubParser.parse(epubFilePath, pathToDecompress)
            decompressedEpubpath = pathToDecompress
            invalidateOptionsMenu()
            onComplete(epubBook)
        }.start()
    }

    private fun setListeners() {
        epubParser.setValidationListeners {
            setOnMetadataMissing { Log.e(ERROR_TAG, "Metadata missing") }
            setOnManifestMissing { Log.e(ERROR_TAG, "Manifest missing") }
            setOnSpineMissing { Log.e(ERROR_TAG, "Spine missing") }
            setOnTableOfContentMissing { Log.e(ERROR_TAG, "Table of contents missing") }
        }
    }

    private fun setMissingAttributesLogger() {
        epubParser.setMissingAttributeLogger {
            setOnAttributeLogger { parentElement, attributeName ->
                Log.w("$parentElement warn", "missing $attributeName attribute")
            }
        }
    }

    private fun showTableOfContents(tocModel: EpubTableOfContentsModel?) {
        val tocAdapter = TableOfContentsAdapter(this)
        with(tocRecycler) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = tocAdapter
        }
        tocModel?.let { tocAdapter.tocModel = it }
        tocAdapter.onItemClickListener = {
            it.location?.let { path -> openFileInWebView("$decompressedEpubpath/$path") }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) = epubBook != null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.book_matadata -> {
            AlertDialog.Builder(this)
                .setTitle(R.string.book_metadata)
                .setAdapter(
                    MetadataAdapter(
                        this,
                        MetadataMapper().mapToViewModel(this, epubBook?.epubMetadataModel)
                    ), null
                )
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private companion object {
        private const val DIR_EPUB_DECOMPRESSED = "epub-uncompressed"
        private const val EPUB_BOOK_NAME = "problems_of_philosophy.epub"
        private const val ERROR_TAG = "EPUB VALIDATION"
    }
}