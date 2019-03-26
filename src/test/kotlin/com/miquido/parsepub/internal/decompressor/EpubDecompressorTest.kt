package com.miquido.parsepub.internal.decompressor

import com.miquido.parsepub.internal.di.ParserModuleProvider
import org.junit.Before
import org.junit.Test
import java.util.zip.ZipEntry
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EpubDecompressorTest {

    private val decompressor: EpubDecompressor by lazy { ParserModuleProvider.epubDecompressor }
    private var entities: List<ZipEntry> = listOf()
    private lateinit var tmpDirPath: String

    @Before
    fun setup() {
        tmpDirPath = createTempDir(DIR_NAME_PREFIX, DIR_NAME_SUFFIX).absolutePath
        entities = decompressor.decompress(
            EPUB_FILE_PATH,
            tmpDirPath
        )
    }

    @Test
    fun `decompressor should return correct files count without any directories`() {
        assertEquals(
            EXPECTED_FILES_COUNT,
            entities.size,
            "Files count differs then expected"
        )
        assertTrue(
            entities.none { it.isDirectory },
            "Decompressor returns a list of files with directories"
        )
    }

    companion object {
        private const val DIR_NAME_PREFIX = "tmp"
        private const val DIR_NAME_SUFFIX = "decompress"
        private const val EPUB_FILE_PATH = "src/test/res/epub/test_ebook.epub"
        private const val EXPECTED_FILES_COUNT = 29
    }
}