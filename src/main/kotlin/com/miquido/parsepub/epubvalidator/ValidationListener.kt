package com.miquido.parsepub.epubvalidator

/**
 * Interface for validation .epub file. Contains methods for handling the missing of important elements in the .epub file.
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

internal class ValidationListeners : ValidationListener {

    private var metadataMissing: (() -> Unit)? = null
    private var manifestMissing: (() -> Unit)? = null
    private var spineMissing: (() -> Unit)? = null
    private var navMapMissing: (() -> Unit)? = null

    fun setMetadataMissing(metadataMissing: (() -> Unit)) {
        this.metadataMissing = metadataMissing
    }

    fun setManifestMissing(manifestMissing: (() -> Unit)) {
        this.manifestMissing = manifestMissing
    }

    fun setSpineMissing(spineMissing: (() -> Unit)) {
        this.spineMissing = spineMissing
    }

    fun setNavMapMissing(navMapMissing: (() -> Unit)) {
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



