package com.miquido.parsepub.internal.cover

import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.parser.EpubManifestParser
import com.miquido.parsepub.model.EpubManifestModel
import com.miquido.parsepub.model.EpubResourceModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class EpubCoverHandlerTest {

    private val parser: EpubManifestParser by lazy { ParserModuleProvider.epubManifestParser }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }
    private val coverHandler: EpubCoverHandler by lazy { ParserModuleProvider.coverHandler }
    private lateinit var document: Document
    private lateinit var manifestModel: EpubManifestModel


    @Before
    fun setup() {
        document = documentBuilder.parse(File(OPF_TEST_FILE_PATH))
        manifestModel = parser.parse(document)
    }

    @Test
    fun `handler method should return correct cover image resource model`() {
        val coverImage = coverHandler.getCoverImageFromManifest(manifestModel)
        assertThat(coverImage).isEqualTo(RESOURCE_MODEL)
    }

    companion object {
        private const val OPF_TEST_FILE_PATH = "src/test/res/opf/epub2/book.opf"
        private val RESOURCE_MODEL = EpubResourceModel(
            "cover-image",
            "OEBPS/assets/TheProblemsOfPhilosophy_1200x1600.jpg",
            "image/jpeg",
            hashSetOf("cover-image")
        )
    }
}