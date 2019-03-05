package com.miquido.parsepub.model

data class EpubManifestModel(val items: List<EpubItemModel>?)

data class EpubItemModel(val id: String, val href: String, val mediaType: String)