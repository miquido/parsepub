package com.miquido.parsepub.internal.di

import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.EpubTableOfContentsParser
import com.miquido.parsepub.internal.parser.EpubManifestParser
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