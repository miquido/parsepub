package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.internal.constants.EpubConstants
import com.miquido.parsepub.internal.extensions.firstWithAttributeNS
import com.miquido.parsepub.internal.extensions.forEach
import com.miquido.parsepub.internal.extensions.getFirstElementByTag
import com.miquido.parsepub.internal.extensions.orValidationError
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal class Epub3TableOfContentsParser : TableOfContentsParser {

    override fun parse(tocDocument: Document, validation: ValidationListener?): EpubTableOfContentsModel {
        val tableOfContentsReferences = mutableListOf<NavigationItemModel>()
        val tocNav = tocDocument.getElementsByTagName(NAV_TAG)
            .orValidationError { validation?.onTableOfContentsMissing() }
            ?.firstWithAttributeNS(EpubConstants.ND_NAMESPACE, TYPE_ATTR, TOC_ATTR_VALUE) as Element

        tocNav.getFirstElementByTag(OL_TAG)
            ?.childNodes.forEach {
            if (it.isNavPoint()) {
                tableOfContentsReferences.add(createNavigationItemModel(it))
            }
        }

        return EpubTableOfContentsModel(tableOfContentsReferences)
    }

    override fun createNavigationItemModel(it: Node): NavigationItemModel {
        val ref = (it as Element).getFirstElementByTag(A_TAG)
        val label = ref?.textContent
        val source = ref?.getAttribute(HREF_ATTR)
        val subItems = createNavigationSubItemModel(it.getFirstElementByTag(OL_TAG)?.childNodes)
        return NavigationItemModel(null, label, source, subItems)
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


    override fun Node.isNavPoint() =
        (this as? Element)?.tagName == "li"

    private companion object {
        private const val OL_TAG = "ol"
        private const val A_TAG = "a"
        private const val NAV_TAG = "nav"
        private const val TYPE_ATTR = "type"
        private const val TOC_ATTR_VALUE = "toc"
        private const val HREF_ATTR = "href"
    }
}