package com.miquido.parsepub.metadata

import com.miquido.parsepub.internal.EpubDecompressor
import org.junit.Before
import org.junit.Test
import java.util.zip.ZipEntry
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EpubDecompressorTest {

    private var decompressor: EpubDecompressor? = null
    private var entities: List<ZipEntry> = listOf()

    @Before
    fun setup() {
        decompressor = EpubDecompressor()
        entities = decompressor?.decompress(EPUB_FILE_PATH, EPUB_DECOMPRESS_PATH) ?: listOf()
    }

    @Test
    fun `decompressor should return correct files count without any directories`() {
        assertEquals(EXPECTED_FILES_COUNT, entities.size, "Files count differs then expected")
        assertTrue(entities.none { it.isDirectory }, "Decompressor returns a list of files with directories")
    }

    companion object {
        private const val EPUB_FILE_PATH = "src/test/res/epub/test_ebook.epub"
        private const val EPUB_DECOMPRESS_PATH = "src/test/res/epub/epubdecompressed"
        private const val EXPECTED_FILES_COUNT = 29
    }
}