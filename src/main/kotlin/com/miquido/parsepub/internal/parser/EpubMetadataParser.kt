package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epubvalidator.ValidationInterface
import com.miquido.parsepub.internal.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.getTagTextContentsFromDcElementOrEmpty
import com.miquido.parsepub.internal.extensions.getTagTextContentsFromDcElementsOrEmpty
import com.miquido.parsepub.internal.extensions.validateElementExt
import com.miquido.parsepub.model.EpubMetadataModel
import org.w3c.dom.Document
import org.w3c.dom.Element

internal class EpubMetadataParser {

    fun parse(opfDocument: Document, validation: ValidationInterface.MetadataValidation?): EpubMetadataModel {
        val metadataElement: Element? = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, METADATA_TAG).validateElementExt { validation?.onMetadataMissing() }

        return EpubMetadataModel(
            creators = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CREATOR_TAG).validateElementExt { validation?.onCreatorsMissing() },
            languages = metadataElement.getTagTextContentsFromDcElementsOrEmpty(LANGUAGE_TAG).validateElementExt { validation?.onLanguagesMissing() },
            contributors = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CONTRIBUTOR_TAG).validateElementExt { validation?.onContributorsMissing() },
            title = metadataElement.getTagTextContentsFromDcElementOrEmpty(TITLE_TAG).validateElementExt { validation?.onTitleMissing() },
            subjects = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SUBJECT_TAG).validateElementExt { validation?.onSubjectMissing() },
            sources = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SOURCE_TAG).validateElementExt { validation?.onSourcesMissing() },
            description = metadataElement.getTagTextContentsFromDcElementOrEmpty(DESCRIPTION_TAG).validateElementExt { validation?.onDescriptionMissing() },
            rights = metadataElement.getTagTextContentsFromDcElementOrEmpty(RIGHTS_TAG).validateElementExt { validation?.onRightsMissing() },
            coverage = metadataElement.getTagTextContentsFromDcElementOrEmpty(COVERAGE_TAG).validateElementExt { validation?.onCoverageMissing() },
            relation = metadataElement.getTagTextContentsFromDcElementOrEmpty(RELATION_TAG).validateElementExt { validation?.onRelationMissing() },
            publisher = metadataElement.getTagTextContentsFromDcElementOrEmpty(PUBLISHER_TAG).validateElementExt { validation?.onPublisherMissing() },
            date = metadataElement.getTagTextContentsFromDcElementOrEmpty(DATE_TAG).validateElementExt { validation?.onDateMissing() },
            id = metadataElement.getTagTextContentsFromDcElementOrEmpty(ID_TAG).validateElementExt { validation?.onIdMissing() }
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