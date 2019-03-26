package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epublogger.AttributeLogger
import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.internal.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.getTagTextContentsFromDcElementOrEmpty
import com.miquido.parsepub.internal.extensions.getTagTextContentsFromDcElementsOrEmpty
import com.miquido.parsepub.internal.extensions.orValidationError
import com.miquido.parsepub.model.EpubMetadataModel
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubMetadataParser {

    internal fun parse(
        opfDocument: Document,
        validation: ValidationListener?,
        attributeLogger: AttributeLogger?
    ): EpubMetadataModel {

        val epubSpecVersion = opfDocument.documentElement.getAttribute(VERSION_ATTR)
        val metadataElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, METADATA_TAG)
            .orValidationError { validation?.onMetadataMissing() }

        return EpubMetadataModel(
            creators = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CREATOR_TAG),
            languages = metadataElement.getTagTextContentsFromDcElementsOrEmpty(LANGUAGE_TAG)
                .orValidationError { attributeLogger?.logMissingAttribute(METADATA_TAG, LANGUAGE_TAG) },
            contributors = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CONTRIBUTOR_TAG),
            title = metadataElement.getTagTextContentsFromDcElementOrEmpty(TITLE_TAG)
                .orValidationError { attributeLogger?.logMissingAttribute(METADATA_TAG, TITLE_TAG) },
            subjects = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SUBJECT_TAG),
            sources = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SOURCE_TAG),
            description = metadataElement.getTagTextContentsFromDcElementOrEmpty(DESCRIPTION_TAG),
            rights = metadataElement.getTagTextContentsFromDcElementOrEmpty(RIGHTS_TAG),
            coverage = metadataElement.getTagTextContentsFromDcElementOrEmpty(COVERAGE_TAG),
            relation = metadataElement.getTagTextContentsFromDcElementOrEmpty(RELATION_TAG),
            publisher = metadataElement.getTagTextContentsFromDcElementOrEmpty(PUBLISHER_TAG),
            date = metadataElement.getTagTextContentsFromDcElementOrEmpty(DATE_TAG),
            id = metadataElement.getTagTextContentsFromDcElementOrEmpty(ID_TAG)
                .orValidationError { attributeLogger?.logMissingAttribute(METADATA_TAG, ID_TAG) },
            epubSpecificationVersion = epubSpecVersion
        )
    }

    private companion object {
        private const val METADATA_TAG = "metadata"
        private const val CREATOR_TAG = "creator"
        private const val CONTRIBUTOR_TAG = "contributor"
        private const val LANGUAGE_TAG = "language"
        private const val TITLE_TAG = "title"
        private const val SUBJECT_TAG = "subject"
        private const val SOURCE_TAG = "source"
        private const val DESCRIPTION_TAG = "description"
        private const val RIGHTS_TAG = "rights"
        private const val COVERAGE_TAG = "coverage"
        private const val RELATION_TAG = "relation"
        private const val PUBLISHER_TAG = "publisher"
        private const val DATE_TAG = "date"
        private const val ID_TAG = "identifier"
        private const val VERSION_ATTR = "version"
    }
}