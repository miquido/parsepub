package com.miquido.parsepub.spine

import com.miquido.parsepub.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.extensions.map
import com.miquido.parsepub.model.EbupSpineReferenceModel
import com.miquido.parsepub.model.EpubSpineModel
import org.w3c.dom.Document
import org.w3c.dom.Element

class EpubSpineParser {
    fun parse(opfDocument: Document): EpubSpineModel {
        val spineElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, SPINE_TAG)
        val spineModel = spineElement?.getElementsByTagNameNS(OPF_NAMESPACE, ITEM_REF_TAG)?.map {
            val element = it as Element
            val idReference = element.getAttribute(ID_REF_ATTR)
            val isLinear = element.getAttribute(IS_LINEAR_ATTR) == IS_LINEAR_POSITIVE_VALUE
            EbupSpineReferenceModel(idReference, isLinear)
        }
        return EpubSpineModel(spineModel)
    }

    companion object {
        private const val SPINE_TAG = "spine"
        private const val ITEM_REF_TAG = "itemref"
        private const val ID_REF_ATTR = "idref"
        private const val IS_LINEAR_ATTR = "linear"
        private const val IS_LINEAR_POSITIVE_VALUE = "yes"
    }
}