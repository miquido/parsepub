package com.miquido.parsepub.extensions

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

fun ZipFile.unpackToPathAndReturnResult(destinationPath: String): List<ZipEntry> {
    val result = mutableListOf<ZipEntry>()
    File(destinationPath).mkdir()

    this.entries().asSequence().forEach { entry ->
        result.add(entry)
        val destinationFile = File(destinationPath, entry.name)
        destinationFile.parentFile.mkdirs()

        if (!entry.isDirectory) {

            val inputStream = BufferedInputStream(this.getInputStream(entry))
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