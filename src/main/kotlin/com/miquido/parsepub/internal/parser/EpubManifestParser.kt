package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.internal.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.epubvalidator.ValidationInterface
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.map
import com.miquido.parsepub.internal.extensions.validateElementExt
import com.miquido.parsepub.model.EpubManifestModel
import com.miquido.parsepub.model.EpubResourceModel
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubManifestParser {

    internal fun parse(opfDocument: Document, validation: ValidationInterface.ManifestValidation?): EpubManifestModel {
        val manifestElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, MANIFEST_TAG).validateElementExt { validation?.onManifestMissing() }
        val itemModel = manifestElement?.getElementsByTagNameNS(OPF_NAMESPACE, ITEM_TAG)?.map {
            val element = it as Element
            val id = element.getAttribute(ID_TAG)?.validateElementExt { validation?.onIdMissing() }
            val href = element.getAttribute(HREF_TAG)?.validateElementExt { validation?.onHrefMissing() }
            val mediaType = element.getAttribute(MEDIA_TYPE_TAG)?.validateElementExt { validation?.onMediaTypeMissing() }
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