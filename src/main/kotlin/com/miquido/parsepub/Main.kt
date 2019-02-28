package com.miquido.parsepub

import com.miquido.parsepub.epubparser.EpubParser
import com.miquido.parsepub.di.parserModule
import org.koin.standalone.StandAloneContext.startKoin

fun main() {
    startKoin(diModules)
    EpubParser().parse("test_ebook.epub", "epub_folder")
    System.exit(0)
}

val diModules = listOf(parserModule)