package com.miquido.parsepub.epublogger

private typealias OnAttributeMissingListener = (parentElement: String, attributeName: String) -> Unit

/**
 * Interface for .epub publication logger. Contains method for inform
 * when the set of attributes in the publication structure element are not complete.
 */
interface AttributeLogger {

    /** Method for logging when attribute are missing.
     *
     * @param parentElement name of publication element where attributes are missing
     * @param attributeName name of missing attribute
     */
    fun logMissingAttribute(parentElement: String, attributeName: String)
}

/**
 * An implementation class for the AttributeLogger interface. Contains override
 * method and setter method which we give body to call
 * in the implemented method.
 */
class MissingAttributeLogger : AttributeLogger {

    private var attributeLogger: (OnAttributeMissingListener)? = null

    /**
     * A setter method that gives the body to call in the overridden
     * logMissingAttribute method.
     *
     * @param attributeLogger lambda expression to be called in an overridden method
     */
    fun setOnAttributeLogger(attributeLogger: OnAttributeMissingListener) {
        this.attributeLogger = attributeLogger
    }

    override fun logMissingAttribute(parentElement: String, attributeName: String) {
        attributeLogger?.invoke(parentElement, attributeName)
    }
}