package com.miquido.parsepub.internal.document.toc

import com.miquido.parsepub.model.EpubManifestModel

internal class Epub3TocLocationFinder {

    fun findNcxLocation(epubManifestModel: EpubManifestModel): String? {
        return epubManifestModel
            .resources
            ?.firstOrNull { it.properties?.contains(NAV_PROPERTY) == true }
            ?.href
    }

    private companion object {
        private const val NAV_PROPERTY = "nav"
    }
}