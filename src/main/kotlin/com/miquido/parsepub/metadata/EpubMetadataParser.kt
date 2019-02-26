package com.miquido.parsepub.metadata

import com.miquido.parsepub.constants.EpubConstants.OPF_NAMESPACE
import com.miquido.parsepub.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.extensions.getTagTextContentsFromDcElementOrEmpty
import com.miquido.parsepub.extensions.getTagTextContentsFromDcElementsOrEmpty
import com.miquido.parsepub.model.EpubMetadataModel
import org.w3c.dom.Document

class EpubMetadataParser {

    fun parse(opfDocument: Document): EpubMetadataModel {
        val metadataElement = opfDocument.getFirstElementByTagNameNS(OPF_NAMESPACE, METADATA_TAG)
        return EpubMetadataModel(
            creators = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CREATOR_TAG),
            languages = metadataElement.getTagTextContentsFromDcElementsOrEmpty(LANGUAGE_TAG),
            contributors = metadataElement.getTagTextContentsFromDcElementsOrEmpty(CONTRIBUTOR_TAG),
            title = metadataElement.getTagTextContentsFromDcElementOrEmpty(TITLE_TAG),
            subject = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SUBJECT_TAG),
            sources = metadataElement.getTagTextContentsFromDcElementsOrEmpty(SOURCE_TAG),
            description = metadataElement.getTagTextContentsFromDcElementOrEmpty(DESCRIPTION_TAG),
            rights = metadataElement.getTagTextContentsFromDcElementOrEmpty(RIGHTS_TAG),
            coverage = metadataElement.getTagTextContentsFromDcElementOrEmpty(COVERAGE_TAG),
            relation = metadataElement.getTagTextContentsFromDcElementOrEmpty(RELATION_TAG),
            publisher = metadataElement.getTagTextContentsFromDcElementOrEmpty(PUBLISHER_TAG),
            date = metadataElement.getTagTextContentsFromDcElementOrEmpty(DATE_TAG),
            id = metadataElement.getTagTextContentsFromDcElementOrEmpty(ID_TAG)
        )
    }


    companion object {
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