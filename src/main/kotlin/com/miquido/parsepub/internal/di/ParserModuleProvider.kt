package com.miquido.parsepub.internal.di

import com.miquido.parsepub.internal.cover.EpubCoverHandler
import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.document.DocumentHandler
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.EpubTableOfContentsParser
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

internal interface ParserModule {
    val epubDecompressor: EpubDecompressor
    val documentBuilder: DocumentBuilder
    val epubSpineParser: EpubSpineParser
    val epubMetadataParser: EpubMetadataParser
    val epubManifestParser: EpubManifestParser
    val epubTableOfContentsParser: EpubTableOfContentsParser
    val documentHandler: DocumentHandler
    val coverHandler: EpubCoverHandler
}

internal object ParserModuleProvider : ParserModule {
    override val epubSpineParser: EpubSpineParser by lazy { EpubSpineParser() }
    override val epubMetadataParser: EpubMetadataParser by lazy { EpubMetadataParser() }
    override val epubManifestParser: EpubManifestParser by lazy { EpubManifestParser() }
    override val epubTableOfContentsParser: EpubTableOfContentsParser by lazy { EpubTableOfContentsParser() }
    override val documentHandler: DocumentHandler by lazy { DocumentHandler() }
    override val epubDecompressor: EpubDecompressor by lazy { EpubDecompressor() }
    override val coverHandler: EpubCoverHandler by lazy { EpubCoverHandler() }
    override val documentBuilder: DocumentBuilder by lazy {
        DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()
    }

}