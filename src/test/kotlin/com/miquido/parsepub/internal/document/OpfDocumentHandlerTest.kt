package com.miquido.parsepub.internal.document

import com.miquido.parsepub.internal.decompressor.EpubDecompressor
import com.miquido.parsepub.internal.di.ParserModuleProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.zip.ZipEntry

class OpfDocumentHandlerTest {

    private var opfFilePath: String? = null
    private lateinit var tmpDirPath: String
    private lateinit var entries: List<ZipEntry>

    private val epubDecompressor: EpubDecompressor by lazy {
        ParserModuleProvider.epubDecompressor
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
        entries = epubDecompressor.decompress(EBOOK_FILE_PATH, tmpDirPath)
        opfFilePath = opfDocumentHandler.getOpfFullFilePath(tmpDirPath, entries)
    }

    @Test
    fun `document handler should return absolute file path for opf file`() {
        assertThat(opfFilePath).isEqualTo(EXPECTED_ABSOLUTE_FILE_PATH)
    }

    @Test
    fun `opf document handler should not return relative file path for opf file`() {
        assertThat(opfFilePath).isNotEqualTo(NON_EXPECTED_RELATIVE_FILE_PATH)
    }

    companion object {
        private const val EBOOK_FILE_PATH = "src/test/res/epub/test_ebook_attr.zip"
        private const val DIR_NAME_PREFIX = "tmp"
        private const val DIR_NAME_SUFFIX = "decompress"
        private const val EXPECTED_ABSOLUTE_FILE_PATH = "test_ebook_attr/book.opf"
        private const val NON_EXPECTED_RELATIVE_FILE_PATH = "book.opf"
    }
}