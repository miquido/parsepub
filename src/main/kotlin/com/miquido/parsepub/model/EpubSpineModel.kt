package com.miquido.parsepub.model

data class EpubSpineModel(val orderedReferences: List<EbupSpineReferenceModel>? = null)

data class EbupSpineReferenceModel(val idReference: String, val linear: Boolean = false)
