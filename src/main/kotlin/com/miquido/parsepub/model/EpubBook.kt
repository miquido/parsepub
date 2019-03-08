package com.miquido.parsepub.model

/**
 * Main model of parsed .epub book. Contains all information extracted from decompressed .pub book.
 *
 * @property epubMetadataModel Model of .epub book metadata. Contains all basic information about the book.
 *
 * @property epubManifestModel Model of .epub book manifest. Contains all book resources.
 *
 * @property epubSpineModel Model of .epub book spine. Contains list of references in reading order.
 *
 * @property epubTableOfContentsModel Model of .epub table of contents.
 */
data class EpubBook(
    val epubMetadataModel: EpubMetadataModel? = null,
    val epubManifestModel: EpubManifestModel? = null,
    val epubSpineModel: EpubSpineModel? = null,
    val epubTableOfContentsModel: EpubTableOfContentsModel? = null
)