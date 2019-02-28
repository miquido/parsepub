package com.miquido.parsepub.epubparser

import com.miquido.parsepub.internal.EpubDecompressor
import org.koin.standalone.KoinComponent
import java.util.zip.ZipEntry

class EpubParser(private val outputPath: String) : KoinComponent {

    private val decompressor = EpubDecompressor()

    fun parseEpubAndReturnEntites(inputPath: String): List<ZipEntry> {
        val zipEntries = decompressor.decompress(inputPath, outputPath)
        return zipEntries
            .filter { !it.isDirectory }
    }
}
