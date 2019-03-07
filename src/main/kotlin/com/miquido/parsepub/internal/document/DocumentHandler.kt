package com.miquido.parsepub.internal.document

import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.extensions.getFirstElementByTag
import org.w3c.dom.Document
import java.io.File
import java.util.zip.ZipEntry
import javax.xml.parsers.DocumentBuilder

internal class DocumentHandler {

    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }

    internal fun createOpfDocument(fileDirPath: String, entries: List<ZipEntry>): Document {
        val containerDocument = parseFileAsDocument(fileDirPath, entries, CONTAINER_HREF)
        val opfFileHref = getOpfFileHref(containerDocument)
        return parseFileAsDocument(fileDirPath, entries, opfFileHref)
    }

    private fun getOpfFileHref(container: Document): String {

        val rootFiles = container.getFirstElementByTag(MAIN_CONTAINER_ROOT_FILES_TAG)
        val rootFile = rootFiles?.getFirstElementByTag(MAIN_CONTAINER_ROOT_FILE_TAG)

        return rootFile?.getAttribute(MAIN_CONTAINER_FULL_PATH_ATTRIBUTE).let {
            if (it.isNullOrEmpty()) DEFAULT_OPF_DOCUMENT_HREF else it
        }
    }

    private fun parseFileAsDocument(fileDirPath: String, entries: List<ZipEntry>, href:String): Document {
        return entries
            .filter { it.name.endsWith(href) }
            .map { documentBuilder.parse(File(fileDirPath + "/${it.name}")) }.firstOrNull() ?: documentBuilder.newDocument()
    }

    companion object {
        const val MAIN_CONTAINER_FULL_PATH_ATTRIBUTE = "full-path"
        const val DEFAULT_OPF_DOCUMENT_HREF = "OEBPS/content.opf"
        const val CONTAINER_HREF = "META-INF/container.xml"
        const val MAIN_CONTAINER_ROOT_FILES_TAG = "rootfiles"
        const val MAIN_CONTAINER_ROOT_FILE_TAG = "rootfile"
    }
}