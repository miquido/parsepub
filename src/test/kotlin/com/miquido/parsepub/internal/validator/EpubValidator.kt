package com.miquido.parsepub.internal.validator

import com.miquido.parsepub.epubvalidator.ValidationListeners

class EpubValidator : ValidationListeners {

    override fun getTableOfContentsListeners(): ValidationListeners.TableOfContentsListeners {
        return EpubTableOfContentsValidator()
    }

    override fun getMetadataListeners(): ValidationListeners.MetadataListeners {
        return EpubMetdataValidator()
    }

    override fun getManifestListeners(): ValidationListeners.ManifestListeners {
        return EpubManifestValidator()
    }

    override fun getSpineListeners(): ValidationListeners.SpineListeners {
        return EpubSpineValidator()
    }
}

class EpubMetdataValidator : ValidationListeners.MetadataListeners {

    override fun onMetadataMissing() {
        println("onMetadataMissing")
    }

    override fun onCreatorsMissing() {
        println("onCreatorsMissing")
    }

    override fun onLanguagesMissing() {
        println("onLanguagesMissing")
    }

    override fun onContributorsMissing() {
        println("onContributorsMissing")
    }

    override fun onTitleMissing() {
        println("onTitleMissing")
    }

    override fun onSubjectMissing() {
        println("onSubjectMissing")
    }

    override fun onSourcesMissing() {
        println("onSourcesMissing")
    }

    override fun onDescriptionMissing() {
        println("onDescriptionMissing")
    }

    override fun onRightsMissing() {
        println("onRightsMissing")
    }

    override fun onCoverageMissing() {
        println("onCoverageMissing")
    }

    override fun onRelationMissing() {
        println("onRelationMissing")
    }

    override fun onPublisherMissing() {
        println("onPublisherMissing")
    }

    override fun onDateMissing() {
        println("onDateMissing")
    }

    override fun onIdMissing() {
        println("onIdMissing")
    }
}

class EpubManifestValidator : ValidationListeners.ManifestListeners {

    override fun onManifestMissing() {
        println("onManifestMissing")
    }

    override fun onIdMissing() {
        println("onIdMissing")
    }

    override fun onHrefMissing() {
        println("onHrefMissing")
    }

    override fun onMediaTypeMissing() {
        println("onMediaTypeMissing")
    }
}

class EpubSpineValidator : ValidationListeners.SpineListeners {

    override fun onSpineMissing() {
        println("onSpineMissing")
    }

    override fun onItemRefMissing() {
        println("onItemRefMissing")
    }

    override fun onIdRefMissing() {
        println("onIdRefMissing")
    }
}

class EpubTableOfContentsValidator : ValidationListeners.TableOfContentsListeners {

    override fun onNavMapMissing() {
        println("onNavMapMissing")
    }

    override fun onNavLabelMissing() {
        println("onNavLabelMissing")
    }

    override fun onTextTagMissing() {
        println("onTextTagMissing")
    }

    override fun onContentTagMissing() {
        println("onContentTagMissing")
    }

    override fun onSrcAttributeMissing() {
        println("onSrcAttributeMissing")
    }
}