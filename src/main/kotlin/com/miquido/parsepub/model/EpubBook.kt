package com.miquido.parsepub.model

data class EpubBook(
    val epubMetadataModel: EpubMetadataModel? = null,
    val epubSpineModel: EpubSpineModel? = null,
    val epubTableOfContentsModel: EpubTableOfContentsModel? = null
)