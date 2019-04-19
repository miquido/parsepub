package com.miquido.parsepub.epubparser

import com.miquido.parsepub.epublogger.AttributeLogger
import com.miquido.parsepub.epublogger.MissingAttributeLogger
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.epubvalidator.ValidationListenersHelper
import com.miquido.parsepub.internal.cover.EpubCoverHandler
import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.OpfDocumentHandler
import com.miquido.parsepub.internal.document.toc.TocDocumentHandler
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.toc.TableOfContentsParserFactory
import com.miquido.parsepub.model.EpubBook

/**
 * Main .epub parser class. Allows to input publication path and parse it into model.
 */
class EpubParser {

    private val decompressor: EpubDecompressor by lazy {
        ParserModuleProvider.epubDecompressor
    }
    private val opfDocumentHandler: OpfDocumentHandler by lazy {
        ParserModuleProvider.opfDocumentHandler
    }
    private val tocDocumentHandler: TocDocumentHandler by lazy {
        ParserModuleProvider.tocDocumentHandler
    }
    private val metadataParser: EpubMetadataParser by lazy {
        ParserModuleProvider.epubMetadataParser
    }
    private val manifestParser: EpubManifestParser by lazy {
        ParserModuleProvider.epubManifestParser
    }
    private val spineParser: EpubSpineParser by lazy {
        ParserModuleProvider.epubSpineParser
    }
    private val tocParserFactory: TableOfContentsParserFactory by lazy {
        ParserModuleProvider.epubTableOfContentsParserFactory
    }
    private val epubCoverHandler: EpubCoverHandler by lazy {
        ParserModuleProvider.epubCoverHandler
    }
    private var validationListeners: ValidationListeners? = null
    private var attributeLogger: AttributeLogger? = null

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

        val epubOpfFilePath = opfDocumentHandler.getOpfFullFilePath(decompressPath, entries)
        val epubManifestModel = manifestParser.parse(
            mainOpfDocument,
            validationListeners,
            attributeLogger
        )
        val epubMetadataModel = metadataParser.parse(
            mainOpfDocument,
            validationListeners,
            attributeLogger
        )
        val tocDocument = tocDocumentHandler.createTocDocument(
            mainOpfDocument,
            entries,
            epubManifestModel,
            decompressPath,
            epubMetadataModel.getEpubSpecificationMajorVersion()
        )

        val epubTocFilePath = tocDocumentHandler.getTocFullFilePath(
            mainOpfDocument,
            entries,
            epubManifestModel,
            epubMetadataModel.getEpubSpecificationMajorVersion()
        )

        return EpubBook(
            epubOpfFilePath,
            epubTocFilePath,
            epubCoverHandler.getCoverImageFromManifest(epubManifestModel),
            epubMetadataModel,
            epubManifestModel,
            spineParser.parse(mainOpfDocument, validationListeners, attributeLogger),
            tocParserFactory.getTableOfContentsParser(
                epubMetadataModel.getEpubSpecificationMajorVersion()
            )
                .parse(tocDocument, validationListeners, attributeLogger)
        )
    }

    /**
     * Setter method that calls the lambda expressions passed in the parameter
     *
     * @param init lambda expressions to call
     */
    fun setValidationListeners(init: ValidationListenersHelper.() -> Unit) {
        val validationListener = ValidationListenersHelper()
        validationListener.init()
        this.validationListeners = validationListener
    }

    /**
     * Setter method that calls the lambda expressions passed in the parameter
     *
     * @param init lambda expressions to call
     */
    fun setMissingAttributeLogger(init: MissingAttributeLogger.() -> Unit) {
        val attributeLogger = MissingAttributeLogger()
        attributeLogger.init()
        this.attributeLogger = attributeLogger
    }
}