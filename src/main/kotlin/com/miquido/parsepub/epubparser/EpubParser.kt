package com.miquido.parsepub.epubparser

import com.miquido.parsepub.decompressor.EpubDecompressor
import com.miquido.parsepub.model.EpubResource
import com.miquido.parsepub.model.EpubResources
import com.miquido.parsepub.model.MediaType
import java.io.File
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class EpubParser(private val outputPath: String) {

    private val decompressor = EpubDecompressor()

    fun parseEpubFromPath(inputPath: String): EpubResources {
        val resourcesMap: HashMap<String, EpubResource> = hashMapOf()
        val zipFile = ZipFile(File(inputPath))
        val zipEntries = decompressor.decompress(inputPath, outputPath)
        zipEntries
            .filter { !it.isDirectory }
            .map { mapToResourceModel(zipFile, it) }
            .forEach { resourcesMap[it.href] = it }
        return EpubResources(resourcesMap)
    }

    private fun mapToResourceModel(zipFile: ZipFile, zipEntry: ZipEntry): EpubResource {
        val inputStream: InputStream = zipFile.getInputStream(zipEntry)
        val mediaType = MediaType.getMediaType(zipEntry.name)
        val content = inputStream.readBytes()
        return EpubResource(href = zipEntry.name, mediaType = mediaType, content = content)
    }
}
