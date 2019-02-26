package com.miquido.parsepub.model

data class EpubMetadataModel(
    val id: String? = null,
    val languages: List<String>? = null,
    val creators: List<String>? = null,
    val contributors: List<String>? = null,
    val title: String? = null,
    val date: String? = null,
    val subject: List<String>? = null,
    val sources: List<String>? = null,
    val description: String? = null,
    val relation: String? = null,
    val coverage: String? = null,
    val rights: String? = null,
    val publisher: String? = null
)