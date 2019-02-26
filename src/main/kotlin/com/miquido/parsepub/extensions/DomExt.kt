package com.miquido.parsepub.extensions

import com.miquido.parsepub.constants.EpubConstants.DC_NAMESPACE
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList

fun Document.getFirstElementByTagNameNS(namespace: String, tag: String): Element? {
    val matchingElements = documentElement.getElementsByTagNameNS(namespace, tag)
    return if (matchingElements.length > 0) matchingElements.item(0) as Element else null
}

fun Element.getFirstElementByTagNameNS(namespace: String, tag: String): Element? {
    val matchingElements = getElementsByTagNameNS(namespace, tag)
    return if (matchingElements.length > 0) matchingElements.item(0) as Element else null
}

fun NodeList?.textContents() = this?.let { nodeList ->
    (0 until nodeList.length)
        .map { index -> nodeList.item(index) }
        .map { creatorNode -> creatorNode.textContent }
        .toList()
}

fun Element?.getTagTextContentsFromDcElementsOrEmpty(tag: String) =
    this?.getElementsByTagNameNS(DC_NAMESPACE, tag).textContents()

fun Element?.getTagTextContentsFromDcElementOrEmpty(tag: String) =
    this?.getFirstElementByTagNameNS(DC_NAMESPACE, tag)?.textContent