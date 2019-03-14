package com.miquido.parsepub.epubvalidator

typealias OnValidationListener = () -> Unit

/**
 * Interface for validation .epub publication. Contains methods for handling the missing of important elements in the .epub publication.
 */
interface ValidationListener {
    /** Method for handling Metadata element missing. */
    fun onMetadataMissing()

    /** Method for handling Manifest element missing. */
    fun onManifestMissing()

    /** Method for handling Spine element missing. */
    fun onSpineMissing()

    /** Method for handling NavMap element missing. */
    fun onNavMapMissing()
}

class ValidationListeners : ValidationListener {

    private var metadataMissing: (OnValidationListener)? = null
    private var manifestMissing: (OnValidationListener)? = null
    private var spineMissing: (OnValidationListener)? = null
    private var navMapMissing: (OnValidationListener)? = null

    fun setMetadataMissing(metadataMissing: OnValidationListener) {
        this.metadataMissing = metadataMissing
    }

    fun setManifestMissing(manifestMissing: OnValidationListener) {
        this.manifestMissing = manifestMissing
    }

    fun setSpineMissing(spineMissing: OnValidationListener) {
        this.spineMissing = spineMissing
    }

    fun setNavMapMissing(navMapMissing: OnValidationListener) {
        this.navMapMissing = navMapMissing
    }

    override fun onMetadataMissing() {
        metadataMissing?.invoke()
    }

    override fun onManifestMissing() {
        manifestMissing?.invoke()
    }

    override fun onSpineMissing() {
        spineMissing?.invoke()
    }

    override fun onNavMapMissing() {
        navMapMissing?.invoke()
    }

}



