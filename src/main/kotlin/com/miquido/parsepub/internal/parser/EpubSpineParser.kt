package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epubvalidator.ValidationInterface
import com.miquido.parsepub.internal.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.map
import com.miquido.parsepub.internal.extensions.validateElementExt
import com.miquido.parsepub.model.EbupSpineReferenceModel
import com.miquido.parsepub.model.EpubSpineModel
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubSpineParser {
    internal fun parse(opfDocument: Document, validation: ValidationInterface.SpineValidation?): EpubSpineModel {
        val spineElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, SPINE_TAG).validateElementExt { validation?.onSpineMissing() }
        val spineModel = spineElement?.getElementsByTagNameNS(OPF_NAMESPACE, ITEM_REF_TAG)
            .validateElementExt { validation?.onItemRefMissing() }
            ?.map {
            val element = it as Element
            val idReference = element.getAttribute(ID_REF_ATTR).validateElementExt { validation?.onIdRefMissing() }
            val isLinear = element.getAttribute(IS_LINEAR_ATTR) == IS_LINEAR_POSITIVE_VALUE
            EbupSpineReferenceModel(idReference, isLinear)
        }
        return EpubSpineModel(spineModel)
    }

    private companion object {
        private const val SPINE_TAG = "spine"
        private const val ITEM_REF_TAG = "itemref"
        private const val ID_REF_ATTR = "idref"
        private const val IS_LINEAR_ATTR = "linear"
        private const val IS_LINEAR_POSITIVE_VALUE = "yes"
    }
}