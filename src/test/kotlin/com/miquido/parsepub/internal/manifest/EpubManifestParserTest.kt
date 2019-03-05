package com.miquido.parsepub.internal.manifest

import com.miquido.parsepub.internal.diModules
import com.miquido.parsepub.model.EpubManifestModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class EpubManifestParserTest : KoinTest {

    private val parser = EpubManifestParser()

    private val documentBuilder: DocumentBuilder by inject()
    private lateinit var document: Document
    private lateinit var manifestModel: EpubManifestModel

    @Before
    fun setup() {
        StandAloneContext.startKoin(diModules)
        document = documentBuilder.parse(File(OPF_TEST_FILE_PATH))
        manifestModel = parser.parse(document)
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `manifest parser should parse all model fields`() {
        assertThat(manifestModel.items).hasSize(EXPECTED_MANIFEST_ITEMS_COUNT)
        val item = manifestModel.items?.get(TEST_ITEM_INDEX)
        assertThat(item?.id).isNotBlank().isEqualTo(ITEM_ID)
        assertThat(item?.href).isNotBlank().isEqualTo(ITEM_HREF)
        assertThat(item?.mediaType).isNotBlank().isEqualTo(ITEM_MEDIA_TYPE)
    }

    companion object {
        private const val OPF_TEST_FILE_PATH = "src/test/res/opf/book.opf"
        private const val EXPECTED_MANIFEST_ITEMS_COUNT = 17
        private const val TEST_ITEM_INDEX = 4
        private const val ITEM_ID = "front-matter-001"
        private const val ITEM_HREF = "OEBPS/front-matter-001-preface.html"
        private const val ITEM_MEDIA_TYPE = "application/xhtml+xml"
    }

}