package com.miquido.parsepub.epublogger

interface AttributeLogger {
    fun logMissingAttribute(parentElement: String, attributeName: String)
}

class MissingAttributeLogger : AttributeLogger {

    private var attributeLogger: ((parentElement: String, attributeName: String) -> Unit)? = null

    fun setOnAttributeLogger(attributeLogger: ((parentElement: String, attributeName: String)-> Unit)) {
        this.attributeLogger = attributeLogger
    }

    override fun logMissingAttribute(parentElement: String, attributeName: String) {
        attributeLogger?.invoke(parentElement, attributeName)
    }
}