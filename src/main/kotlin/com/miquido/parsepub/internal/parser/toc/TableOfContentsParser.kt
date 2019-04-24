package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.epublogger.AttributeLogger
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.constants.EpubConstants.EPUB_MAJOR_VERSION_3
import com.miquido.parsepub.model.EpubTableOfContentsModel
import org.w3c.dom.Document

internal interface TableOfContentsParser {
    fun parse(
        tocDocument: Document?,
        validation: ValidationListeners?,
        attributeLogger: AttributeLogger? = null
    ): EpubTableOfContentsModel
}

internal class TableOfContentsParserFactory {

    fun getTableOfContentsParser(epuSpecMajorVersion: Int?): TableOfContentsParser {
        return if (epuSpecMajorVersion == EPUB_MAJOR_VERSION_3) {
            Epub3TableOfContentsParser()
        } else {
            Epub2TableOfContentsParser()
        }
    }
}