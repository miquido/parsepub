package com.miquido.parsepub.internal.di

import com.miquido.parsepub.internal.metadata.EpubMetadataParser
import com.miquido.parsepub.internal.spine.EpubSpineParser
import com.miquido.parsepub.internal.tableofcontents.EpubTableOfContentsParser
import com.miquido.parsepub.internal.manifest.EpubManifestParser
import org.koin.dsl.module.module
import javax.xml.parsers.DocumentBuilderFactory

internal val parserModule = module {
    single {
        DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()
    }
    single { EpubSpineParser() }
    single { EpubMetadataParser() }
    single { EpubTableOfContentsParser() }
    single { EpubManifestParser() }
}