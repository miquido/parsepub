package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.validator.TestEpubValidator
import com.miquido.parsepub.model.EpubManifestModel
import com.miquido.parsepub.model.EpubResourceModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class EpubManifestParserTest {

    private val parser: EpubManifestParser by lazy { ParserModuleProvider.epubManifestParser }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }
    private lateinit var document: Document
    private lateinit var manifestModel: EpubManifestModel
    private lateinit var validator: ValidationListener

    @Before
    fun setup() {
        validator = TestEpubValidator()
        document = documentBuilder.parse(File(OPF_TEST_FILE_PATH))
        manifestModel = parser.parse(document, validator)
    }

    @Test
    fun `manifest parser should parse all model fields`() {
        assertThat(manifestModel.resources).hasSize(EXPECTED_MANIFEST_ITEMS_COUNT)
        assertThat(manifestModel.resources).containsExactlyElementsOf(EXPECTED_MANIFEST_ELEMENTS)
    }

    companion object {
        private const val OPF_TEST_FILE_PATH = "src/test/res/opf/epub2/book.opf"
        private const val EXPECTED_MANIFEST_ITEMS_COUNT = 6
        private val EXPECTED_MANIFEST_ELEMENTS = listOf(
            EpubResourceModel("front-cover", "OEBPS/front-cover.html", "application/xhtml+xml"),
            EpubResourceModel("front-matter-001", "OEBPS/front-matter-001-preface.html", "application/xhtml+xml"),
            EpubResourceModel("chapter-001", "OEBPS/chapter-001-appearance-and-reality.html", "application/xhtml+xml"),
            EpubResourceModel("cover-image2", "OEBPS/assets/TheProblemsOfPhilosophy_1200x1600.jpg", "image/jpeg"),
            EpubResourceModel(
                "cover-image",
                "OEBPS/assets/TheProblemsOfPhilosophy_1200x1600.jpg",
                "image/jpeg",
                hashSetOf("cover-image")
            ),
            EpubResourceModel("ncx", "toc.ncx", "application/x-dtbncx+xml")
        )
    }

}