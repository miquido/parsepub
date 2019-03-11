package com.miquido.parsepub.epubvalidator

interface ValidationListeners {

    fun getMetadataListeners(): MetadataListeners
    fun getManifestListeners(): ManifestListeners
    fun getSpineListeners(): SpineListeners
    fun getTableOfContentsListeners(): TableOfContentsListeners

    interface MetadataListeners {
        fun onMetadataMissing()
        fun onCreatorsMissing()
        fun onLanguagesMissing()
        fun onContributorsMissing()
        fun onTitleMissing()
        fun onSubjectMissing()
        fun onSourcesMissing()
        fun onDescriptionMissing()
        fun onRightsMissing()
        fun onCoverageMissing()
        fun onRelationMissing()
        fun onPublisherMissing()
        fun onDateMissing()
        fun onIdMissing()
    }

    interface ManifestListeners {
        fun onManifestMissing()
        fun onIdMissing()
        fun onHrefMissing()
        fun onMediaTypeMissing()
    }

    interface SpineListeners {
        fun onSpineMissing()
        fun onItemRefMissing()
        fun onIdRefMissing()
    }

    interface TableOfContentsListeners {
        fun onNavMapMissing()
        fun onNavLabelMissing()
        fun onTextTagMissing()
        fun onContentTagMissing()
        fun onSrcAttributeMissing()
    }
}

