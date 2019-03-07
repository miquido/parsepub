package com.miquido.parsepubsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.miquido.parsepub.epubparser.EpubParser
import com.miquido.parsepub.model.EpubBook
import com.miquido.parsepubsample.tableofcontents.TableOfContentsFragment
import java.io.File

class MainActivity : AppCompatActivity(), EpubBookProcessor {

    private val epubParser = EpubParser()
    private var epubBook: EpubBook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TableOfContentsFragment())
            .commit()
    }

    override fun getEpubBook() = epubBook

    override fun parseEpubBook(onComplete: (result: EpubBook?) -> Unit) {
        Thread {
            val epubFilePath = copyFileFromAssets(EPUB_BOOK_NAME, cacheDir.path)
            epubBook = epubParser.parse(epubFilePath, "$filesDir${File.separator}$DIR_EPUB_UNCOMPRESSED")
            invalidateOptionsMenu()
            onComplete(epubBook)
        }.start()
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
                .setMessage(epubBook?.epubMetadataModel.toString())
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private companion object {
        private const val DIR_EPUB_UNCOMPRESSED = "epub-uncompressed"
        private const val EPUB_BOOK_NAME = "problems_of_philosophy.epub"
    }
}

interface EpubBookProcessor {
    fun getEpubBook(): EpubBook?
    fun parseEpubBook(onComplete: (epubBook: EpubBook?) -> Unit)
}