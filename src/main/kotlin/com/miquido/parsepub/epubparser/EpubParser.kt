package com.miquido.parsepub.epubparser

import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.NcxDocumentHandler
import com.miquido.parsepub.internal.document.OpfDocumentHandler
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
    private val ncxDocumentHandler: NcxDocumentHandler by lazy { ParserModuleProvider.ncxDocumentHandler }
    private val metadataParser: EpubMetadataParser by lazy { ParserModuleProvider.epubMetadataParser }
    private val manifestParser: EpubManifestParser by lazy { ParserModuleProvider.epubManifestParser }
    private val spineParser: EpubSpineParser by lazy { ParserModuleProvider.epubSpineParser }
    private val tableOfContentsParser: EpubTableOfContentsParser by lazy { ParserModuleProvider.epubTableOfContentsParser }
    private var listener: ValidationListener? = null
    /**
     * Function allowing to parse .epub publication into model and handling errors
     *
     * @param inputPath Path of .epub publication for parsing
     * @param decompressPath Path to which .epub publication will be decompressed
     * @param validation class object used to call methods responsible for handling individual validations
     * @return Parsed .epub publication model
     */
    fun parse(inputPath: String, decompressPath: String, validation: ValidationListener?): EpubBook {
        val entries = decompressor.decompress(inputPath, decompressPath)
        val mainOpfDocument = opfDocumentHandler.createOpfDocument(decompressPath, entries)
        val epubManifestModel = manifestParser.parse(mainOpfDocument, validation)
        val ncxDocument = ncxDocumentHandler.createNcxDocument(mainOpfDocument, epubManifestModel, decompressPath)

        setListeners {
            setMetadataMissing { validation?.onMetadataMissing() }
            setManifestMissing { validation?.onManifestMissing() }
            setSpineMissing { validation?.onSpineMissing() }
            setNavMapMissing { validation?.onNavMapMissing() }
        }

        return EpubBook(
            metadataParser.parse(mainOpfDocument, validation),
            epubManifestModel,
            spineParser.parse(mainOpfDocument, validation),
            tableOfContentsParser.parse(ncxDocument, validation)
        )
    }

    private fun setListeners(init: ValidationListeners.() -> Unit) {
        val listener = ValidationListeners()
        listener.init()
        this.listener = listener
    }
}
