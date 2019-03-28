package com.miquido.parsepub.internal.document

import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.extensions.getFirstElementByTag
import org.w3c.dom.Document
import java.io.File
import java.util.zip.ZipEntry
import javax.xml.parsers.DocumentBuilder

internal class OpfDocumentHandler {

    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }

    internal fun createOpfDocument(fileDirPath: String, entries: List<ZipEntry>): Document {
        val opfFileHref = getOpfFileHref(fileDirPath, entries)
        return parseFileAsDocument(fileDirPath, entries, opfFileHref)
    }

    internal fun getOpfFullFilePath(fileDirPath: String, entries: List<ZipEntry>): String? {
        val opfFileHref = getOpfFileHref(fileDirPath, entries)
        return entries.firstOrNull { it.name.endsWith(opfFileHref) }?.name
    }

    private fun getOpfFileHref(fileDirPath: String, entries: List<ZipEntry>): String {
        val containerDocument = parseFileAsDocument(fileDirPath, entries, CONTAINER_HREF)
        return getOpfFileHref(containerDocument)
    }

    private fun getOpfFileHref(container: Document): String {

        val rootFiles = container.getFirstElementByTag(MAIN_CONTAINER_ROOT_FILES_TAG)
        val rootFile = rootFiles?.getFirstElementByTag(MAIN_CONTAINER_ROOT_FILE_TAG)

        return rootFile?.getAttribute(MAIN_CONTAINER_FULL_PATH_ATTRIBUTE).let {
            if (it.isNullOrEmpty()) DEFAULT_OPF_DOCUMENT_HREF else it
        }
    }

    private fun parseFileAsDocument(
        fileDirPath: String,
        entries: List<ZipEntry>,
        href: String
    ): Document {

        return entries
            .filter { it.name.endsWith(href) }
            .map { documentBuilder.parse(File(fileDirPath + "/${it.name}")) }
            .firstOrNull()
            ?: documentBuilder.newDocument()
    }

    companion object {
        const val MAIN_CONTAINER_FULL_PATH_ATTRIBUTE = "full-path"
        const val DEFAULT_OPF_DOCUMENT_HREF = "OEBPS/content.opf"
        const val CONTAINER_HREF = "META-INF/container.xml"
        const val MAIN_CONTAINER_ROOT_FILES_TAG = "rootfiles"
        const val MAIN_CONTAINER_ROOT_FILE_TAG = "rootfile"
    }
}