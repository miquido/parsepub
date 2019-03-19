package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.epubvalidator.AttributeLogger
import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.internal.constants.EpubConstants.EPUB_MAJOR_VERSION_3
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList


interface TableOfContentsParser {
    fun parse(tocDocument: Document?, validation: ValidationListener?, attributeLogger: AttributeLogger? = null): EpubTableOfContentsModel
    fun createNavigationItemModel(it: Node): NavigationItemModel
    fun createNavigationSubItemModel(childrenNodes: NodeList?): List<NavigationItemModel>
    fun Node.isNavPoint(): Boolean
}


internal class TableOfContentParserFactory {

    fun getTableOfContentsParser(epuSpecMajorVersion: Int?) = if (epuSpecMajorVersion == EPUB_MAJOR_VERSION_3) {
        Epub3TableOfContentsParser()
    } else {
        Epub2TableOfContentsParser()
    }
}