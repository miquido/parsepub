package com.miquido.parsepub.internal.document

import com.miquido.parsepub.internal.extensions.getFirstElementByTag
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import org.w3c.dom.Document
import java.io.File
import java.util.zip.ZipEntry
import javax.xml.parsers.DocumentBuilder

internal class DocumentHandler(private val outputPath: String) : KoinComponent {

    private val documentBuilder: DocumentBuilder by inject()

    internal fun createOpfDocument(entries: List<ZipEntry>): Document {
        val containerDocument = generateDocument(entries,
            CONTAINER_HREF
        )
        val opfFileHref = getOpfFileHref(containerDocument)
        return generateDocument(entries, opfFileHref)
    }

    private fun getOpfFileHref(container: Document): String {
        val defaultHref = DEFAULT_OPF_DOCUMENT_HREF

        val rootFiles = container.getFirstElementByTag(MAIN_CONTAINER_ROOT_FILES_TAG)
        val rootFile = rootFiles?.getFirstElementByTag(MAIN_CONTAINER_ROOT_FILE_TAG)

        return rootFile?.getAttribute(MAIN_CONTAINER_FULL_PATH_ATTRIBUTE).let {
            if (it.isNullOrEmpty()) defaultHref else it
        }
    }

    private fun generateDocument(entries: List<ZipEntry>, href:String): Document {
        return entries
            .filter { it.name.endsWith(href) }
            .map { documentBuilder.parse(File(outputPath + "/${it.name}")) }.firstOrNull() ?: documentBuilder.newDocument()
    }

    companion object {
        const val MAIN_CONTAINER_FULL_PATH_ATTRIBUTE = "full-path"
        const val DEFAULT_OPF_DOCUMENT_HREF = "OEBPS/content.opf"
        const val CONTAINER_HREF = "META-INF/container.xml"
        const val MAIN_CONTAINER_ROOT_FILES_TAG = "rootfiles"
        const val MAIN_CONTAINER_ROOT_FILE_TAG = "rootfile"
    }
}