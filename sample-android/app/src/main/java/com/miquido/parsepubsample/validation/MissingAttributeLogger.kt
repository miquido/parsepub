package com.miquido.parsepubsample.validation

import android.util.Log
import com.miquido.parsepub.epubvalidator.AttributeLogger

class MissingAttributeLogger: AttributeLogger {

    override fun logMissingAttribute(parentElement: String, attributeName: String) {
        Log.e("MISSING ATTR LOGGER", "missing $attributeName attribute in $parentElement element")
    }
}