package com.miquido.parsepub.model

data class EpubTableOfContentsModel(val tableOfContents: List<NavigationItemModel>)

data class NavigationItemModel(
    val id: String? = null,
    val label: String? = null,
    val source: String? = null,
    val subItems: List<NavigationItemModel>? = null
)