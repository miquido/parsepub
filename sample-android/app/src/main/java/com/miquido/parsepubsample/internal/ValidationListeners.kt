package com.miquido.parsepubsample.internal

import android.util.Log
import com.miquido.parsepub.epubvalidator.ValidationListener

internal class ValidationListeners : ValidationListener {

    override fun onManifestMissing() {
        Log.e(ERROR_TAG, "Manifest Missing")
    }

    override fun onMetadataMissing() {
        Log.e(ERROR_TAG, "Metadata Missing")
    }

    override fun onNavMapMissing() {
        Log.e(ERROR_TAG, "Navigation Map Missing")
    }

    override fun onSpineMissing() {
        Log.e(ERROR_TAG, "Spine Missing")
    }

    companion object {
        private const val ERROR_TAG = "EPUB VALIDATION"
    }
}