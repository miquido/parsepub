package com.miquido.parsepub.internal.spine

import com.miquido.parsepub.internal.diModules
import com.miquido.parsepub.model.EpubSpineModel
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

class SpineParserTest : KoinTest {

    private val parser = EpubSpineParser()

    private val documentBuilder: DocumentBuilder by inject()
    private lateinit var document: Document
    private lateinit var spineModel: EpubSpineModel


    @Before
    fun setup() {
        StandAloneContext.startKoin(diModules)
        document = documentBuilder.parse(File(OPF_TEST_FILE_PATH))
        spineModel = parser.parse(document)
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `metadata parser should parse all model fields`() {
        assertThat(spineModel.orderedReferences).hasSize(EXPECTED_SPINE_SIZE)
    }

    companion object {
        private const val OPF_TEST_FILE_PATH = "src/test/res/opf/book.opf"
        private const val EXPECTED_SPINE_SIZE = 8

    }
}