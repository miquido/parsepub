package com.miquido.parsepubsample.tableofcontents

import android.content.Context
import com.miquido.parsepub.model.EpubMetadataModel
import com.miquido.parsepubsample.R

class MetadataMapper {
    fun mapToViewModel(context: Context, epubMetadataModel: EpubMetadataModel?): Array<Pair<String, String?>> {
        return arrayOf(
            context.getString(R.string.id) to epubMetadataModel?.id,
            context.getString(R.string.languages) to epubMetadataModel?.languages?.joinToString(separator = MULTI_VALUE_SEPARATOR),
            context.getString(R.string.creators) to epubMetadataModel?.creators?.joinToString(separator = MULTI_VALUE_SEPARATOR),
            context.getString(R.string.contributors) to epubMetadataModel?.contributors?.joinToString(separator = MULTI_VALUE_SEPARATOR),
            context.getString(R.string.title) to epubMetadataModel?.title,
            context.getString(R.string.date) to epubMetadataModel?.date,
            context.getString(R.string.subjects) to epubMetadataModel?.subjects?.joinToString(separator = MULTI_VALUE_SEPARATOR),
            context.getString(R.string.sources) to epubMetadataModel?.sources?.joinToString(separator = MULTI_VALUE_SEPARATOR),
            context.getString(R.string.description) to epubMetadataModel?.description,
            context.getString(R.string.relation) to epubMetadataModel?.relation,
            context.getString(R.string.coverage) to epubMetadataModel?.coverage,
            context.getString(R.string.rights) to epubMetadataModel?.rights,
            context.getString(R.string.publisher) to epubMetadataModel?.publisher
        )
    }

    private companion object {
        private const val MULTI_VALUE_SEPARATOR = ", "
    }
}