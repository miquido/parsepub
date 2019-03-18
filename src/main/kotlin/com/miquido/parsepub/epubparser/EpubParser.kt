package com.miquido.parsepub.epubparser

import com.miquido.parsepub.epubvalidator.AttributeLogger
import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.OpfDocumentHandler
import com.miquido.parsepub.internal.document.toc.TocDocumentHandler
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.toc.TableOfContentParserFactory
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
    private val tocParserFactory: TableOfContentParserFactory by lazy { ParserModuleProvider.epubTableOfContentsParserFactory }
    private var validationListener: ValidationListener? = null

    /**
     * Function allowing to parse .epub publication into model
     *
     * @param inputPath Path of .epub publication for parsing
     * @param decompressPath Path to which .epub publication will be decompressed
     * @return Parsed .epub publication model
     */
    fun parse(inputPath: String, decompressPath: String, attributeLogger: AttributeLogger? = null): EpubBook {
        val entries = decompressor.decompress(inputPath, decompressPath)
        val mainOpfDocument = opfDocumentHandler.createOpfDocument(decompressPath, entries)

        val epubManifestModel = manifestParser.parse(mainOpfDocument, validationListener, attributeLogger)
        val epubMetadataModel = metadataParser.parse(mainOpfDocument, validationListener, attributeLogger)
        val tocDocument = tocDocumentHandler.createTocDocument(
            mainOpfDocument, epubManifestModel,
            decompressPath, epubMetadataModel.getEpubSpecificationMajorVersion()
        )
        return EpubBook(
            epubMetadataModel,
            epubManifestModel,
            spineParser.parse(mainOpfDocument, validationListener, attributeLogger),
            tocParserFactory.getTableOfContentsParser(epubMetadataModel.getEpubSpecificationMajorVersion())
                .parse(tocDocument, validationListener)
        )
    }

    fun setValidationListeners(init: ValidationListeners.() -> Unit) {
        val validationListener = ValidationListeners()
        validationListener.init()
        this.validationListener = validationListener
    }
}