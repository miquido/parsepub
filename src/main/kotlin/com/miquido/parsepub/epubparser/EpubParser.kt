package com.miquido.parsepub.epubparser

import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.DocumentHandler
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.EpubTableOfContentsParser
import com.miquido.parsepub.model.EpubBook
import java.io.File
import javax.xml.parsers.DocumentBuilder

/**
 * Main .epub parser class. Allows to input .epub book and return their model.
 */
class EpubParser {

    private val decompressor: EpubDecompressor by lazy { ParserModuleProvider.epubDecompressor }
    private val documentHandler: DocumentHandler by lazy { ParserModuleProvider.documentHandler }
    private val metadataParser: EpubMetadataParser by lazy { ParserModuleProvider.epubMetadataParser }
    private val manifestParser: EpubManifestParser by lazy { ParserModuleProvider.epubManifestParser }
    private val spineParser: EpubSpineParser by lazy { ParserModuleProvider.epubSpineParser }
    private val tableOfContentsParser: EpubTableOfContentsParser by lazy { ParserModuleProvider.epubTableOfContentsParser }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }
    /**
     * Method allowing to parse .epub book into model
     *
     * @param inputPath Path of .epub book for parsing
     * @param decompressPath Path to which .epub book will be decompressed
     * @return Parsed .epub book model
     */
    fun parse(inputPath: String, decompressPath: String): EpubBook {
        val entries = decompressor.decompress(inputPath, decompressPath)
        val mainOpfDocument = documentHandler.createOpfDocument(decompressPath, entries)

        return EpubBook(
            metadataParser.parse(mainOpfDocument),
            manifestParser.parse(mainOpfDocument),
            spineParser.parse(mainOpfDocument),
            //TODO temporary - add code to find .ncx document with fallback
            tableOfContentsParser.parse(documentBuilder.parse(File(decompressPath).walkTopDown().first {
                it.path.endsWith(".ncx")
            }))
        )
    }
}

