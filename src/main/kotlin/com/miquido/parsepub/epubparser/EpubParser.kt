package com.miquido.parsepub.epubparser

import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.OpfDocumentHandler
import com.miquido.parsepub.internal.document.toc.TocDocumentHandler
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.EpubTableOfContentsParser
import com.miquido.parsepub.model.EpubBook

/**
 * Main .epub parser class. Allows to input publication path and parse it into model.
 */
class EpubParser {

    private val decompressor: EpubDecompressor by lazy { ParserModuleProvider.epubDecompressor }
    private val opfDocumentHandler: OpfDocumentHandler by lazy { ParserModuleProvider.opfDocumentHandler }
    private val tocDocumentHandler: TocDocumentHandler by lazy { ParserModuleProvider.tocDocumentHandler }
    private val metadataParser: EpubMetadataParser by lazy { ParserModuleProvider.epubMetadataParser }
    private val manifestParser: EpubManifestParser by lazy { ParserModuleProvider.epubManifestParser }
    private val spineParser: EpubSpineParser by lazy { ParserModuleProvider.epubSpineParser }
    private val tableOfContentsParser: EpubTableOfContentsParser by lazy { ParserModuleProvider.epubTableOfContentsParser }

    /**
     * Function allowing to parse .epub publication into model
     *
     * @param inputPath Path of .epub publication for parsing
     * @param decompressPath Path to which .epub publication will be decompressed
     * @return Parsed .epub publication model
     */
    fun parse(inputPath: String, decompressPath: String): EpubBook {
        val entries = decompressor.decompress(inputPath, decompressPath)
        val mainOpfDocument = opfDocumentHandler.createOpfDocument(decompressPath, entries)

        val epubManifestModel = manifestParser.parse(mainOpfDocument)
        val epubMetadataModel = metadataParser.parse(mainOpfDocument)
        val ncxDocument = tocDocumentHandler.createTocDocument(
            mainOpfDocument, epubManifestModel,
            decompressPath, epubMetadataModel.getEpubSpecificationMajorVersion()
        )
        return EpubBook(
            epubMetadataModel,
            epubManifestModel,
            spineParser.parse(mainOpfDocument),
            tableOfContentsParser.parse(ncxDocument)
        )
    }
}

