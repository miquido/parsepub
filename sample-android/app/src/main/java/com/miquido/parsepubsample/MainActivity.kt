package com.miquido.parsepubsample

import android.os.Bundle
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
            onComplete(epubBook)
        }.start()
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