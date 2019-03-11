package com.miquido.parsepub.epubvalidator

interface ValidationInterface {

    fun getMetadataInterface(): MetadataValidation
    fun getManifestInterface(): ManifestValidation
    fun getSpineInterface(): SpineValidation

    interface MetadataValidation {
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

    interface ManifestValidation {
        fun onManifestMissing()
        fun onIdMissing()
        fun onHrefMissing()
        fun onMediaTypeMissing()
    }

    interface SpineValidation {
        fun onSpineMissing()
        fun onItemRefMissing()
        fun onIdRefMissing()
    }
}

