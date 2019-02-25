package com.miquido.parsepub.model

class EpubResource(
    var id: String? = null,
    var href: String,
    var mediaType: MediaType? = null,
    var content: ByteArray
)