package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.constants.EpubConstants.NCX_NAMESPACE
import com.miquido.parsepub.internal.extensions.forEach
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.orNullIfEmpty
import com.miquido.parsepub.internal.extensions.orValidationError
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal class Epub2TableOfContentsParser : EpubTableOfContentsParser() {

    private var validationListeners: ValidationListeners? = null

    override fun parse(
        tocDocument: Document?,
        validationListeners: ValidationListeners?
    ): EpubTableOfContentsModel {

        this.validationListeners = validationListeners
        val tableOfContentsReferences = mutableListOf<NavigationItemModel>()
        tocDocument?.getFirstElementByTagNameNS(NCX_NAMESPACE, NAV_MAP_TAG)
            .orValidationError {
                validationListeners?.onTableOfContentsMissing()
            }
            ?.childNodes.forEach {
            if (it.isNavPoint()) {
                tableOfContentsReferences.add(createNavigationItemModel(it))
            } else {
                orValidationError {
                    validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, NAV_POINT_TAG)
                }
            }
        }
        return EpubTableOfContentsModel(tableOfContentsReferences)
    }

    override fun createNavigationItemModel(node: Node): NavigationItemModel {
        val element = node as Element
        val id = element.getAttribute(ID_ATTR)
            .orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, ID_ATTR)
            }
        val label = element.getFirstElementByTagNameNS(NCX_NAMESPACE, NAV_LABEL_TAG)
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, NAV_LABEL_TAG)
            }
            ?.getFirstElementByTagNameNS(NCX_NAMESPACE, TEXT_TAG)?.textContent
            ?.orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, TEXT_TAG)
            }
        val source = element.getFirstElementByTagNameNS(NCX_NAMESPACE, CONTENT_TAG)
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, CONTENT_TAG)
            }
            ?.getAttribute(SRC_ATTR)
            ?.orNullIfEmpty()
            .orValidationError {
                validationListeners?.onAttributeMissing(TABLE_OF_CONTENTS_TAG, SRC_ATTR)
            }
        val subItems = createNavigationSubItemModel(element.childNodes)
        return NavigationItemModel(id, label, source, subItems)
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

    override fun Node.isNavPoint() = (this as? Element)?.tagName == NAV_POINT_TAG

    private companion object {
        private const val TABLE_OF_CONTENTS_TAG = "table of contents"
        private const val NAV_MAP_TAG = "navMap"
        private const val NAV_POINT_TAG = "navPoint"
        private const val NAV_LABEL_TAG = "navLabel"
        private const val CONTENT_TAG = "content"
        private const val TEXT_TAG = "text"
        private const val SRC_ATTR = "src"
        private const val ID_ATTR = "id"
    }
}