package com.miquido.parsepub.metadata

import com.miquido.parsepub.internal.EpubDecompressor
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class EpubDecompressorTest {

    private var decompressor: EpubDecompressor? = null

    @Before
    fun setup() {
        decompressor = EpubDecompressor()
    }

    @Test
    fun `decompressor should return correct files count`() {
        assertEquals(EXPECTED_FILES_COUNT, decompressor?.decompress(EPUB_FILE_PATH, EPUB_DECOMPRESS_PATH)?.size, "Files count differs then expected")
    }

    companion object {
        private const val EPUB_FILE_PATH = "src/test/res/epub/test_ebook.epub"
        private const val EPUB_DECOMPRESS_PATH = "src/test/res/epub/epubdecompressed"
        private const val EXPECTED_FILES_COUNT = 29
    }
}