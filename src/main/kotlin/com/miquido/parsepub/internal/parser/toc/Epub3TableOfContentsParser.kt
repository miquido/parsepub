package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.epublogger.AttributeLogger
import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.internal.constants.EpubConstants
import com.miquido.parsepub.internal.extensions.*
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal class Epub3TableOfContentsParser : TableOfContentsParser {

    private var validationAttr: AttributeLogger? = null

    override fun parse(
        tocDocument: Document?,
        validation: ValidationListener?,
        attributeLogger: AttributeLogger?
    ): EpubTableOfContentsModel {

        this.validationAttr = attributeLogger
        val tableOfContentsReferences = mutableListOf<NavigationItemModel>()
        val tocNav = tocDocument?.getElementsByTagName(NAV_TAG)
            .orValidationError { validation?.onTableOfContentsMissing() }
            ?.firstWithAttributeNS(EpubConstants.ND_NAMESPACE, TYPE_ATTR, TOC_ATTR_VALUE)
            .orValidationError {
                attributeLogger?.logMissingAttribute(TOC_ATTR_VALUE, TABLE_OF_CONTENTS_TAG)
            } as Element

        tocNav.getFirstElementByTag(OL_TAG)
            .orValidationError {
                attributeLogger?.logMissingAttribute(OL_TAG, TABLE_OF_CONTENTS_TAG)
            }
            ?.childNodes.forEach {
            if (it.isNavPoint()) {
                tableOfContentsReferences.add(createNavigationItemModel(it))
            } else {
                orValidationError {
                    attributeLogger?.logMissingAttribute(TABLE_OF_CONTENTS_TAG, NAV_POINT_TAG)
                }
            }
        }

        return EpubTableOfContentsModel(tableOfContentsReferences)
    }

    override fun createNavigationItemModel(it: Node): NavigationItemModel {
        val ref = (it as Element).getFirstElementByTag(A_TAG)
        val label = ref?.textContent
            ?.orNullIfEmpty()
            .orValidationError {
                validationAttr?.logMissingAttribute(LABEL_FIELD_NAME, TABLE_OF_CONTENTS_TAG)
            }
        val source = ref?.getAttribute(HREF_ATTR)
            ?.orNullIfEmpty()
            .orValidationError {
                validationAttr?.logMissingAttribute(HREF_ATTR, TABLE_OF_CONTENTS_TAG)
            }
        val subItems = createNavigationSubItemModel(it.getFirstElementByTag(OL_TAG)?.childNodes)
        return NavigationItemModel(null, label, source, subItems)
    }

    override fun createNavigationSubItemModel(childrenNodes: NodeList?): List<NavigationItemModel> {
        val navSubItems = mutableListOf<NavigationItemModel>()
        childrenNodes?.forEach {
            if (it.isNavPoint()) {
                createNavigationItemModel(it).let { navigationItem ->
                    navSubItems.add(navigationItem)
                }
            }
        }
        return navSubItems
    }

    override fun Node.isNavPoint() =
        (this as? Element)?.tagName == NAV_POINT_TAG

    private companion object {
        private const val NAV_POINT_TAG = "li"
        private const val OL_TAG = "ol"
        private const val A_TAG = "a"
        private const val NAV_TAG = "nav"
        private const val LABEL_FIELD_NAME = "label"
        private const val TYPE_ATTR = "type"
        private const val TOC_ATTR_VALUE = "toc"
        private const val HREF_ATTR = "href"
        private const val TABLE_OF_CONTENTS_TAG = "table of contents"
    }
}