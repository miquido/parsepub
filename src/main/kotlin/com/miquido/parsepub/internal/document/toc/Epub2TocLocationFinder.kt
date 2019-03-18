package com.miquido.parsepub.internal.document.toc

import com.miquido.parsepub.internal.constants.EpubConstants
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.model.EpubManifestModel
import org.w3c.dom.Document

internal class Epub2TocLocationFinder {
    private fun fallbackFindNcxPath(epubManifestModel: EpubManifestModel): String? {
        return epubManifestModel.resources?.firstOrNull { NCX_LOCATION_REGEXP.toRegex().matches(it.href.orEmpty()) }
            ?.href
    }

    internal fun findNcxPath(mainOpfDocument: Document, epubManifestModel: EpubManifestModel): String? {
        val ncxResourceId = mainOpfDocument.getFirstElementByTagNameNS(EpubConstants.OPF_NAMESPACE, SPINE_TAG)
            ?.getAttribute(TOC_ATTR)
        var ncxLocation = epubManifestModel.resources?.firstOrNull { it.id == ncxResourceId }?.href

        if (ncxLocation?.isEmpty() == true) {
            ncxLocation = Epub2TocLocationFinder().fallbackFindNcxPath(epubManifestModel)
        }

        return ncxLocation
    }

    private companion object {
        private const val SPINE_TAG = "spine"
        private const val TOC_ATTR = "toc"
        private const val NCX_LOCATION_REGEXP = ".*\\.ncx"
    }
}