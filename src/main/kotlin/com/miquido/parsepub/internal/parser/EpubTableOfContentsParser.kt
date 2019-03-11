package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.internal.constants.EpubConstants.NCX_NAMESPACE
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.extensions.forEach
import com.miquido.parsepub.internal.extensions.getFirstElementByTagNameNS
import com.miquido.parsepub.internal.extensions.orValidationError
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal class EpubTableOfContentsParser {

    private var validationListener: ValidationListeners.TableOfContentsListeners? = null

    internal fun parse(ncxDocument: Document, validation: ValidationListeners.TableOfContentsListeners?): EpubTableOfContentsModel {
        validationListener = validation
        val tableOfContentsReferences = mutableListOf<NavigationItemModel>()
        ncxDocument.getFirstElementByTagNameNS(NCX_NAMESPACE, NAV_MAP_TAG)
            .orValidationError { validationListener?.onNavMapMissing() }
            ?.childNodes.forEach {
            if (it.isNavPoint()) {
                tableOfContentsReferences.add(createNavigationItemModel(it))
            }
        }
        return EpubTableOfContentsModel(tableOfContentsReferences)
    }

    private fun createNavigationItemModel(node: Node): NavigationItemModel {
        val element = node as Element
        val id = element.getAttribute(ID_ATTR)
        val label = element.getFirstElementByTagNameNS(NCX_NAMESPACE, NAV_LABEL_TAG)
            .orValidationError { validationListener?.onNavLabelMissing() }
            ?.getFirstElementByTagNameNS(NCX_NAMESPACE, TEXT_TAG)
            .orValidationError { validationListener?.onTextTagMissing() }
            ?.textContent
        val source = element.getFirstElementByTagNameNS(NCX_NAMESPACE, CONTENT_TAG)
            .orValidationError { validationListener?.onContentTagMissing() }
            ?.getAttribute(SRC_ATTR).orValidationError { validationListener?.onSrcAttributeMissing() }
        val subItems = createNavigationSubItemModel(element.childNodes)
        return NavigationItemModel(id, label, source, subItems)
    }

    private fun createNavigationSubItemModel(childrenNodes: NodeList?): List<NavigationItemModel> {
        val navSubItems = mutableListOf<NavigationItemModel>()
        childrenNodes?.forEach {
            if (it.isNavPoint()) {
                createNavigationItemModel(it).let { navSubItems.add(it) }
            }
        }
        return navSubItems
    }

    private fun Node.isNavPoint() = (this as? Element)?.tagName == NAV_POINT_TAG

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
