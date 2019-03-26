package com.miquido.parsepub.internal.document.toc

import com.miquido.parsepub.model.EpubManifestModel

internal class Epub3TocLocationFinder {

    fun findNcxPath(epubManifestModel: EpubManifestModel): String? {
        val resources = epubManifestModel.resources
        val ncxResourceId = resources
                ?.firstOrNull { it.properties?.contains(NAV_PROPERTY) == true }
                ?.id
        return resources?.firstOrNull { it.id == ncxResourceId }?.href
    }

    private companion object {
        private const val NAV_PROPERTY = "nav"
    }
}