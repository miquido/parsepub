package com.miquido.parsepub.internal.validator

import com.miquido.parsepub.epubvalidator.ValidationListener

class EpubValidator : ValidationListener {

    override fun onMetadataMissing() {
        println("onMetadataMissing")
    }

    override fun onManifestMissing() {
        println("onManifestMissing")
    }

    override fun onSpineMissing() {
        println("onSpineMissing")
    }

    override fun onNavMapMissing() {
        println("onNavMapMissing")
    }

}