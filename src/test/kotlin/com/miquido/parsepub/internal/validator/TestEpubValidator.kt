package com.miquido.parsepub.internal.validator

import com.miquido.parsepub.epubvalidator.ValidationListener

class TestEpubValidator : ValidationListener {

    override fun onMetadataMissing() {
        println("onMetadataMissing")
    }

    override fun onManifestMissing() {
        println("onManifestMissing")
    }

    override fun onSpineMissing() {
        println("onSpineMissing")
    }

    override fun onTableOfContentsMissing() {
        println("onTableOfContentsMissing")
    }

}