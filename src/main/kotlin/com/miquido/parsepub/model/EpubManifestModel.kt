package com.miquido.parsepub.model

data class EpubManifestModel(val resources: List<EpubResourceModel>?)

data class EpubResourceModel(val id: String, val href: String, val mediaType: String, val properties: String? = "")