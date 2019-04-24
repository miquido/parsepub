package com.miquido.parsepub.internal.parser.toc

import com.miquido.parsepub.model.NavigationItemModel
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal abstract class EpubTableOfContentsParser : TableOfContentsParser{
    protected abstract fun createNavigationItemModel(it: Node): NavigationItemModel
    protected abstract fun createNavigationSubItemModel(childrenNodes: NodeList?): List<NavigationItemModel>
    protected abstract fun Node.isNavPoint(): Boolean
}