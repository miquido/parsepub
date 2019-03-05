package com.miquido.parsepub.internal.manifest

import com.miquido.parsepub.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.map
import com.miquido.parsepub.model.EpubItemModel
import com.miquido.parsepub.model.EpubManifestModel
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubManifestParser {

    internal fun parse(opfDocument: Document): EpubManifestModel {
        val manifestElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, MANIFEST_TAG)
        val itemModel = manifestElement?.getElementsByTagNameNS(OPF_NAMESPACE, ITEM_TAG)?.map {
            val element = it as Element
            val id = element.getAttribute(ID_TAG)
            val href = element.getAttribute(HREF_TAG)
            val mediaType = element.getAttribute(MEDIA_TYPE_TAG)
            EpubItemModel(id, href, mediaType)
        }
        return EpubManifestModel(itemModel)
    }

    companion object {
        private const val MANIFEST_TAG = "manifest"
        private const val ITEM_TAG = "item"
        private const val ID_TAG = "id"
        private const val HREF_TAG = "href"
        private const val MEDIA_TYPE_TAG = "media-type"
    }
}