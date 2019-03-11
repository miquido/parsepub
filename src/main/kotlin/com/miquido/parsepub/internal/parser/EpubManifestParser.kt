package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.map
import com.miquido.parsepub.internal.extensions.orValidationError
import com.miquido.parsepub.model.EpubManifestModel
import com.miquido.parsepub.model.EpubResourceModel
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubManifestParser {

    internal fun parse(opfDocument: Document, validation: ValidationListeners.ManifestListeners?): EpubManifestModel {
        val manifestElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, MANIFEST_TAG).orValidationError { validation?.onManifestMissing() }
        val itemModel = manifestElement?.getElementsByTagNameNS(OPF_NAMESPACE, ITEM_TAG)?.map {
            val element = it as Element
            val id = element.getAttribute(ID_TAG)?.orValidationError { validation?.onIdMissing() }
            val href = element.getAttribute(HREF_TAG)?.orValidationError { validation?.onHrefMissing() }
            val mediaType = element.getAttribute(MEDIA_TYPE_TAG)?.orValidationError { validation?.onMediaTypeMissing() }
            val properties = element.getAttribute(PROPERTIES_TAG)
            EpubResourceModel(id, href, mediaType, properties)
        }
        return EpubManifestModel(itemModel)
    }

    companion object {
        private const val MANIFEST_TAG = "manifest"
        private const val ITEM_TAG = "item"
        private const val ID_TAG = "id"
        private const val HREF_TAG = "href"
        private const val MEDIA_TYPE_TAG = "media-type"
        private const val PROPERTIES_TAG = "properties"
    }
}