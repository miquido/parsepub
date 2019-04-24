package com.miquido.parsepub.epubvalidator

private typealias OnValidationListener = () -> Unit

/**
 * Interface for validation .epub publication. Contains methods for handling
 * the missing of important elements in the .epub publication.
 */
interface ValidationListeners {

    /** Method for handling Metadata element missing. */
    fun onMetadataMissing()

    /** Method for handling Manifest element missing. */
    fun onManifestMissing()

    /** Method for handling Spine element missing. */
    fun onSpineMissing()

    /** Method for handling table of contents element missing. */
    fun onTableOfContentsMissing()
}

/**
 * An implementation class for the ValidationListeners interface. Contains override
 * interface methods and setter methods which we give body to call
 * in the implemented methods.
 */
class ValidationListenersHelper : ValidationListeners {

    private var metadataMissing: (OnValidationListener)? = null
    private var manifestMissing: (OnValidationListener)? = null
    private var spineMissing: (OnValidationListener)? = null
    private var tableOfContentsMissing: (OnValidationListener)? = null

    /**
     * A setter method that gives the body to call in the overridden
     * onMetadataMissing method.
     *
     * @param metadataMissing lambda expression to be called in an overridden method
     */
    fun setOnMetadataMissing(metadataMissing: OnValidationListener) {
        this.metadataMissing = metadataMissing
    }

    /**
     * A setter method that gives the body to call in the overridden
     * onManifestMissing method.
     *
     * @param manifestMissing lambda expression to be called in an overridden method
     */
    fun setOnManifestMissing(manifestMissing: OnValidationListener) {
        this.manifestMissing = manifestMissing
    }

    /**
     * A setter method that gives the body to call in the overridden
     * onSpineMissing method.
     *
     * @param spineMissing lambda expression to be called in an overridden method
     */
    fun setOnSpineMissing(spineMissing: OnValidationListener) {
        this.spineMissing = spineMissing
    }

    /**
     * A setter method that gives the body to call in the overridden
     * onTableOfContentsMissing method.
     *
     * @param tableOfContentsMissing lambda expression to be called in an overridden method
     */
    fun setOnTableOfContentsMissing(tableOfContentsMissing: OnValidationListener) {
        this.tableOfContentsMissing = tableOfContentsMissing
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
        tableOfContentsMissing?.invoke()
    }

}



