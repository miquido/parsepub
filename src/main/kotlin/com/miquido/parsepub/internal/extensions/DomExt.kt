package com.miquido.parsepub.internal.extensions

import com.miquido.parsepub.constants.EpubConstants.DC_NAMESPACE
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal fun Document.getFirstElementByTagNameNS(namespace: String, tag: String): Element? {
    val matchingElements = documentElement.getElementsByTagNameNS(namespace, tag)
    return matchingElements.isNotEmpty()
}

internal fun Element.getFirstElementByTagNameNS(namespace: String, tag: String): Element? {
    val matchingElements = getElementsByTagNameNS(namespace, tag)
    return matchingElements.isNotEmpty()
}

internal fun Element.getFirstElementByTag(tag: String): Element? {
    val matchingElements = this.getElementsByTagName(tag)
    return matchingElements.isNotEmpty()
}

internal fun Document.getFirstElementByTag(tag: String): Element? {
    val matchingElements = this.documentElement.getElementsByTagName(tag)
    return matchingElements.isNotEmpty()
}

internal fun NodeList.isNotEmpty(): Element? {
    return this.let {
        if (it.length > 0) it.item(0) as Element else null
    }
}

internal fun NodeList?.textContents() = this?.let { nodeList ->
    (0 until nodeList.length)
        .map { index -> nodeList.item(index) }
        .map { creatorNode -> creatorNode.textContent }
        .toList()
}

internal fun Element?.getTagTextContentsFromDcElementsOrEmpty(tag: String) =
    this?.getElementsByTagNameNS(DC_NAMESPACE, tag).textContents()

internal fun Element?.getTagTextContentsFromDcElementOrEmpty(tag: String) =
    this?.getFirstElementByTagNameNS(DC_NAMESPACE, tag)?.textContent

internal inline fun <R> NodeList.map(transform: (Node) -> R): List<R> {
    val result = mutableListOf<R>()
    return (0 until length).map { index -> item(index) }.mapTo(result, transform)
}

internal fun NodeList?.forEach(action: (Node) -> Unit) {
    this?.let {
        for (i in 0 until length) {
            action(item(i))
        }
    }
}