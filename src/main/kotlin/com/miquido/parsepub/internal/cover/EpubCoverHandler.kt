package com.miquido.parsepub.internal.cover

import com.miquido.parsepub.model.EpubManifestModel
import com.miquido.parsepub.model.EpubResourceModel

internal class EpubCoverHandler {

    internal fun getCoverImageFromManifest(manifestModel: EpubManifestModel): EpubResourceModel? {

        var coverImage = manifestModel
                .resources
                ?.firstOrNull { it.properties?.contains(COVER_IMAGE_ID_NAME) == true }

        if (coverImage == null) {
            coverImage = manifestModel.resources
                ?.firstOrNull {
                    it.id?.contains(COVER_RESOURCE_VALUE, ignoreCase = true)
                    it.mediaType?.contains(IMAGE_LABEL) == true
                }
        }
        return coverImage
    }

    companion object {
        private const val COVER_IMAGE_ID_NAME = "cover-image"
        private const val COVER_RESOURCE_VALUE = "cover"
        private const val IMAGE_LABEL = "image"
    }
}
