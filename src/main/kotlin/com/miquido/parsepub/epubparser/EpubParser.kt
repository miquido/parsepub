package com.miquido.parsepub.epubparser

import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.document.DocumentHandler

class EpubParser {

    private val decompressor = EpubDecompressor()
    private val documentHandler = DocumentHandler()

    fun parse(inputPath: String, outputPath: String) {
        val entries = decompressor.decompress(inputPath, outputPath)
        documentHandler.createOpfDocument(outputPath, entries)
    }
}
