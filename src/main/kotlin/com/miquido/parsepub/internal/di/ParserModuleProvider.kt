package com.miquido.parsepub.internal.di

import com.miquido.parsepub.internal.cover.EpubCoverHandler
import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.document.OpfDocumentHandler
import com.miquido.parsepub.internal.document.toc.TocDocumentHandler
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.toc.TableOfContentsParserFactory
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

internal interface ParserModule {
    val epubDecompressor: EpubDecompressor
    val documentBuilder: DocumentBuilder
    val epubSpineParser: EpubSpineParser
    val epubMetadataParser: EpubMetadataParser
    val epubManifestParser: EpubManifestParser
    val epubTableOfContentsParserFactory: TableOfContentsParserFactory
    val opfDocumentHandler: OpfDocumentHandler
    val epubCoverHandler: EpubCoverHandler
    val tocDocumentHandler: TocDocumentHandler
}

internal object ParserModuleProvider : ParserModule {
    override val epubSpineParser: EpubSpineParser by lazy { EpubSpineParser() }
    override val epubMetadataParser: EpubMetadataParser by lazy { EpubMetadataParser() }
    override val epubManifestParser: EpubManifestParser by lazy { EpubManifestParser() }
    override val epubTableOfContentsParserFactory: TableOfContentsParserFactory by lazy {
        TableOfContentsParserFactory()
    }
    override val opfDocumentHandler: OpfDocumentHandler by lazy { OpfDocumentHandler() }
    override val epubDecompressor: EpubDecompressor by lazy { EpubDecompressor() }
    override val epubCoverHandler: EpubCoverHandler by lazy { EpubCoverHandler() }
    override val documentBuilder: DocumentBuilder by lazy {
        DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()
    }
    override val tocDocumentHandler: TocDocumentHandler by lazy { TocDocumentHandler() }
}