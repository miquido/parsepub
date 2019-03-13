package com.miquido.parsepub.internal.document

import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.document.toc.Epub3TocLocationFinder
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.model.EpubManifestModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilder

class Epub3TocLocationFinderTest {

    private val epub3TocLocationFinder = Epub3TocLocationFinder()

    private val manifestParser: EpubManifestParser by lazy { ParserModuleProvider.epubManifestParser }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }
    private lateinit var epub3ManifestModel: EpubManifestModel

    @Before
    fun setup() {
        epub3ManifestModel = manifestParser.parse(documentBuilder.parse(File(OPF_EPUB3_TEST_FILE_PATH)))
    }

    @Test
    fun `document handler should return correct path for epub3 specification`() {
        val location = epub3TocLocationFinder.findNcxPath(epub3ManifestModel)
        assertThat(location).isEqualTo(EXPECTED_EPUB_3_TOC_LOCATION)
    }


    private companion object {
        private const val OPF_EPUB3_TEST_FILE_PATH = "src/test/res/opf/epub3/book.opf"

        private const val EXPECTED_EPUB_3_TOC_LOCATION = "TOC.xhtml"
    }
}