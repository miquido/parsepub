package com.miquido.parsepub.epubparser

import com.miquido.parsepub.internal.decompressor.EpubDecompressor

class EpubParser {

    private val decompressor = EpubDecompressor()

    fun parse(inputPath: String, outputPath: String) {
        decompressor.decompress(inputPath, outputPath)
    }
}
