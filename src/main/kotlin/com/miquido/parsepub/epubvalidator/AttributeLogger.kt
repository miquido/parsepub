package com.miquido.parsepub.epubvalidator

interface AttributeLogger {
    fun logMissingAttribute(parentElement: String, attributeName: String)
}