package com.miquido.parsepub.decompressor

import com.miquido.parsepub.extensions.unpackToPathAndReturnResult
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class EpubDecompressor {

    fun decompress(zipFilePath: String, outputPath: String): List<ZipEntry>  {
        val zipFile = ZipFile(File(zipFilePath))
        return zipFile.unpackToPathAndReturnResult(outputPath)
    }
}