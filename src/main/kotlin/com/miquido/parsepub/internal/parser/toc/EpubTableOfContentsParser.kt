package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Node
import org.w3c.dom.NodeList

abstract class EpubTableOfContentsParser : TableOfContentsParser{
    abstract fun createNavigationItemModel(it: Node): NavigationItemModel
    abstract fun createNavigationSubItemModel(childrenNodes: NodeList?): List<NavigationItemModel>
    abstract fun Node.isNavPoint(): Boolean
}