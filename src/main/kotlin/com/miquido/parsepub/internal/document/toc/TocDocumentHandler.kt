package com.miquido.parsepub.internal.document.toc

import com.miquido.parsepub.internal.constants.EpubConstants.EPUB_MAJOR_VERSION_3
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.model.EpubManifestModel
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class TocDocumentHandler {

    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }

    fun createTocDocument(
        mainOpfDocument: Document,
        epubManifestModel: EpubManifestModel,
        decompressPath: String,
        epubSpecMajorVersion: Int?
    ): Document {
        val tocLocation = if (epubSpecMajorVersion == EPUB_MAJOR_VERSION_3) {
            Epub3TocLocationFinder().findNcxPath(epubManifestModel)
        } else {
            Epub2TocLocationFinder().findNcxPath(mainOpfDocument, epubManifestModel)
        }

        //TODO handle error if ncxLocation still empty

        return documentBuilder.parse(File("$decompressPath/$tocLocation"))
    }
}

