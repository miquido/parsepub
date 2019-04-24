package com.miquido.parsepub.internal.document

import com.miquido.parsepub.epublogger.AttributeLogger
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.toc.TocDocumentHandler
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.internal.parser.EpubMetadataParser
import com.miquido.parsepub.model.EpubManifestModel
import com.miquido.parsepub.model.EpubMetadataModel
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import java.util.zip.ZipEntry

class TocDocumentHandlerTest {

    private var tocFilePath: String? = null
    private lateinit var tmpDirPath: String
    private lateinit var epubEntries: List<ZipEntry>
    private lateinit var mainOpfFile: Document
    private lateinit var manifestModel: EpubManifestModel
    private lateinit var metadataModel: EpubMetadataModel
    private val validator = mock<ValidationListeners>()
    private val attributeLogger = mock<AttributeLogger>()

    private val manifestParser: EpubManifestParser by lazy {
        ParserModuleProvider.epubManifestParser
    }
    private val metadataParser: EpubMetadataParser by lazy {
        ParserModuleProvider.epubMetadataParser
    }
    private val epubDecompressor: EpubDecompressor by lazy {
        ParserModuleProvider.epubDecompressor
    }
    private val tocDocumentHandler: TocDocumentHandler by lazy {
        ParserModuleProvider.tocDocumentHandler
    }
    private val opfDocumentHandler: OpfDocumentHandler by lazy {
        ParserModuleProvider.opfDocumentHandler
    }


    @Before
    fun setup() {
        tmpDirPath = createTempDir(
            DIR_NAME_PREFIX,
            DIR_NAME_SUFFIX
        ).absolutePath

        epubEntries = epubDecompressor.decompress(EBOOK_FILE_PATH, tmpDirPath)
        mainOpfFile = opfDocumentHandler.createOpfDocument(tmpDirPath, epubEntries)
        manifestModel = manifestParser.parse(mainOpfFile, validator, attributeLogger)
        metadataModel = metadataParser.parse(mainOpfFile, validator, attributeLogger)

        tocFilePath = tocDocumentHandler.getTocFullFilePath(
            mainOpfFile,
            epubEntries,
            manifestModel,
            metadataModel.getEpubSpecificationMajorVersion()
        )
    }

    @Test
    fun `toc document handler should return absolute file path for toc file`() {
        assertThat(tocFilePath).isEqualTo(EXPECTED_ABSOLUTE_FILE_PATH)
    }

    @Test
    fun `toc document handler should not return relative file path for toc file`() {
        assertThat(tocFilePath).isNotEqualTo(NON_EXPECTED_RELATIVE_FILE_PATH)
    }

    companion object {
        private const val EBOOK_FILE_PATH = "src/test/res/epub/test_ebook_attr.zip"
        private const val DIR_NAME_PREFIX = "tmp"
        private const val DIR_NAME_SUFFIX = "decompress"
        private const val EXPECTED_ABSOLUTE_FILE_PATH = "test_ebook_attr/toc.ncx"
        private const val NON_EXPECTED_RELATIVE_FILE_PATH = "toc.ncx"
    }
}