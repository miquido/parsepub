package com.miquido.parsepub.internal.document

import com.miquido.parsepub.internal.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.model.EpubManifestModel
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

internal class NcxDocumentHandler {

    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }

    internal fun createNcxDocument(
        mainOpfDocument: Document,
        epubManifestModel: EpubManifestModel,
        decompressPath: String
    ): Document {
        val ncxResourceId = mainOpfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, SPINE_TAG)
            ?.getAttribute(TOC_ATTR)
        var ncxLocation = epubManifestModel.resources?.firstOrNull { it.id == ncxResourceId }?.href.orEmpty()

        // fallback: if ncx location attribute not present, find ncx location by name
        if (ncxLocation.isEmpty()) {
            ncxLocation = epubManifestModel.resources
                ?.firstOrNull { NCX_LOCATION_REGEXP.toRegex().matches(it.href.orEmpty()) }?.href.orEmpty()
        }

        //TODO handle error if ncxLocation still empty

        return documentBuilder.parse(File("$decompressPath/$ncxLocation"))
    }

    private companion object {
        private const val SPINE_TAG = "spine"
        private const val TOC_ATTR = "toc"
        private const val NCX_LOCATION_REGEXP = ".*\\.ncx"
    }
}
