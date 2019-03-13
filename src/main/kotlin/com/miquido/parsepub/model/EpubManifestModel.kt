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
 * @property properties List of property values
 */
data class EpubResourceModel(val id: String, val href: String, val mediaType: String, val properties: HashSet<String>? = null)