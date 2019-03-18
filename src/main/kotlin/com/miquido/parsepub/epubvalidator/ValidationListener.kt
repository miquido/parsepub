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

    /** Method for handling table of contents element missing. */
    fun onTableOfContentsMissing()
}

class ValidationListeners : ValidationListener {

    private var metadataMissing: (OnValidationListener)? = null
    private var manifestMissing: (OnValidationListener)? = null
    private var spineMissing: (OnValidationListener)? = null
    private var tableOfContentsMissingMissing: (OnValidationListener)? = null

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
        this.tableOfContentsMissingMissing = navMapMissing
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

    override fun onTableOfContentsMissing() {
        tableOfContentsMissingMissing?.invoke()
    }

}



