package com.miquido.parsepub.model

/**
 * Epub manifest model. Contains exhaustive list of all publication resources.
 *
 * @property resources List of all resources available in publication.
 */
data class EpubManifestModel(val resources: List<EpubResourceModel>?)

/**
 * Model of single publication resource.
 *
 * @property id Id of the resource
 * @property href Location of the resource
 * @property mediaType Type and format of the resource
 * @property properties Set of property values
 */
data class EpubResourceModel(
    val id: String? = null,
    val href: String? = null,
    val mediaType: String? = null,
    val properties: HashSet<String>? = null
)
