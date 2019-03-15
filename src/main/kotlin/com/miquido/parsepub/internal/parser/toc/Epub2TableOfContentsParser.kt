package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.internal.constants.EpubConstants
import com.miquido.parsepub.internal.extensions.forEach
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal class Epub2TableOfContentsParser : TableOfContentsParser {

    override fun parse(tocDocument: Document): EpubTableOfContentsModel {
        val tableOfContentsReferences = mutableListOf<NavigationItemModel>()
        tocDocument.getFirstElementByTagNameNS(EpubConstants.NCX_NAMESPACE, NAV_MAP_TAG)?.childNodes.forEach {
            if (it.isNavPoint()) {
                tableOfContentsReferences.add(createNavigationItemModel(it))
            }
        }
        return EpubTableOfContentsModel(tableOfContentsReferences)
    }

    override fun createNavigationItemModel(node: Node): NavigationItemModel {
        val element = node as Element
        val id = element.getAttribute(ID_ATTR)
        val label = element.getFirstElementByTagNameNS(EpubConstants.NCX_NAMESPACE, NAV_LABEL_TAG)
            ?.getFirstElementByTagNameNS(EpubConstants.NCX_NAMESPACE, TEXT_TAG)?.textContent
        val source = element.getFirstElementByTagNameNS(EpubConstants.NCX_NAMESPACE, CONTENT_TAG)?.getAttribute(SRC_ATTR)
        val subItems = createNavigationSubItemModel(element.childNodes)
        return NavigationItemModel(id, label, source, subItems)
    }

    override fun createNavigationSubItemModel(childrenNodes: NodeList?): List<NavigationItemModel> {
        val navSubItems = mutableListOf<NavigationItemModel>()
        childrenNodes?.forEach {
            if (it.isNavPoint()) {
                createNavigationItemModel(it).let { navSubItems.add(it) }
            }
        }
        return navSubItems
    }

    override fun Node.isNavPoint() = (this as? Element)?.tagName == NAV_POINT_TAG

    private companion object {
        private const val NAV_MAP_TAG = "navMap"
        private const val NAV_POINT_TAG = "navPoint"
        private const val NAV_LABEL_TAG = "navLabel"
        private const val CONTENT_TAG = "content"
        private const val TEXT_TAG = "text"
        private const val SRC_ATTR = "src"
        private const val ID_ATTR = "id"
    }
}