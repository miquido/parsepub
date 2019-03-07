package com.miquido.parsepub.epubparser

import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.DocumentHandler
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.internal.parser.EpubSpineParser
import com.miquido.parsepub.internal.parser.EpubTableOfContentsParser
import com.miquido.parsepub.model.EpubBook
import java.io.File
import javax.xml.parsers.DocumentBuilder

class EpubParser {

    private val decompressor: EpubDecompressor by lazy { ParserModuleProvider.epubDecompressor }
    private val documentHandler: DocumentHandler by lazy { ParserModuleProvider.documentHandler }
    private val metadataParser: EpubMetadataParser by lazy { ParserModuleProvider.epubMetadataParser }
    private val spineParser: EpubSpineParser by lazy { ParserModuleProvider.epubSpineParser }
    private val tableOfContentsParser: EpubTableOfContentsParser by lazy { ParserModuleProvider.epubTableOfContentsParser }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }

    fun parse(inputPath: String, outputPath: String): EpubBook {
        val entries = decompressor.decompress(inputPath, outputPath)
        val mainOpfDocument = documentHandler.createOpfDocument(outputPath, entries)

        return EpubBook(
            metadataParser.parse(mainOpfDocument),
            spineParser.parse(mainOpfDocument),
            //TODO temporary - add code to find .ncx document with fallback
            tableOfContentsParser.parse(documentBuilder.parse(File(outputPath).walkTopDown().first {
                it.path.endsWith(".ncx")
            }))
        )
    }
}
