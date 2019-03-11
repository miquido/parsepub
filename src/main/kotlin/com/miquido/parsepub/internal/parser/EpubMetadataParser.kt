package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.internal.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.getTagTextContentsFromDcElementOrEmpty
import com.miquido.parsepub.internal.extensions.getTagTextContentsFromDcElementsOrEmpty
import com.miquido.parsepub.internal.extensions.orValidationError
import com.miquido.parsepub.model.EpubMetadataModel
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubMetadataParser {

    fun parse(opfDocument: Document, validation: ValidationListeners.MetadataListeners?): EpubMetadataModel {
        val metadataElement: Element? = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, METADATA_TAG).orValidationError { validation?.onMetadataMissing() }

        return EpubMetadataModel(
            creators = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CREATOR_TAG).orValidationError { validation?.onCreatorsMissing() },
            languages = metadataElement.getTagTextContentsFromDcElementsOrEmpty(LANGUAGE_TAG).orValidationError { validation?.onLanguagesMissing() },
            contributors = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CONTRIBUTOR_TAG).orValidationError { validation?.onContributorsMissing() },
            title = metadataElement.getTagTextContentsFromDcElementOrEmpty(TITLE_TAG).orValidationError { validation?.onTitleMissing() },
            subjects = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SUBJECT_TAG).orValidationError { validation?.onSubjectMissing() },
            sources = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SOURCE_TAG).orValidationError { validation?.onSourcesMissing() },
            description = metadataElement.getTagTextContentsFromDcElementOrEmpty(DESCRIPTION_TAG).orValidationError { validation?.onDescriptionMissing() },
            rights = metadataElement.getTagTextContentsFromDcElementOrEmpty(RIGHTS_TAG).orValidationError { validation?.onRightsMissing() },
            coverage = metadataElement.getTagTextContentsFromDcElementOrEmpty(COVERAGE_TAG).orValidationError { validation?.onCoverageMissing() },
            relation = metadataElement.getTagTextContentsFromDcElementOrEmpty(RELATION_TAG).orValidationError { validation?.onRelationMissing() },
            publisher = metadataElement.getTagTextContentsFromDcElementOrEmpty(PUBLISHER_TAG).orValidationError { validation?.onPublisherMissing() },
            date = metadataElement.getTagTextContentsFromDcElementOrEmpty(DATE_TAG).orValidationError { validation?.onDateMissing() },
            id = metadataElement.getTagTextContentsFromDcElementOrEmpty(ID_TAG).orValidationError { validation?.onIdMissing() }
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
    }
}