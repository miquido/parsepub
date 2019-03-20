package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.model.EpubSpineModel
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class SpineParserTest {

    private val parser: EpubSpineParser by lazy { ParserModuleProvider.epubSpineParser }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }
    private lateinit var document: Document
    private lateinit var spineModel: EpubSpineModel
    private val validator = mock<ValidationListener>()

    @Before
    fun setup() {
        document = documentBuilder.parse(File(OPF_TEST_FILE_PATH))
        spineModel = parser.parse(document, validator)
    }

    @Test
    fun `metadata parser should parse all model fields`() {
        assertThat(spineModel.orderedReferences).hasSize(EXPECTED_SPINE_SIZE)
    }

    companion object {
        private const val OPF_TEST_FILE_PATH = "src/test/res/opf/epub2/book.opf"
        private const val EXPECTED_SPINE_SIZE = 8
    }
}