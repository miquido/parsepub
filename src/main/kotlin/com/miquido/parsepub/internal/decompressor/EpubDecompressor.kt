package com.miquido.parsepub.internal.decompressor

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

internal class EpubDecompressor {

    fun decompress(zipFilePath: String, outputPath: String): List<ZipEntry> {
        val zipFile = ZipFile(File(zipFilePath))
        return unpackToPathAndReturnResult(zipFile, outputPath)
    }

    private fun unpackToPathAndReturnResult(zipFile: ZipFile,
                                            outputPath: String
    ): List<ZipEntry> {

        val result = mutableListOf<ZipEntry>()
        File(outputPath).mkdir()

        zipFile.entries().asSequence().forEach { entry ->
            result.add(entry)
            val destinationFile = File(outputPath, entry.name)
            destinationFile.parentFile.mkdirs()

            if (!entry.isDirectory) {
                val inputStream = BufferedInputStream(zipFile.getInputStream(entry))
                val outputStream = BufferedOutputStream(FileOutputStream(destinationFile))

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
        return result
    }
}