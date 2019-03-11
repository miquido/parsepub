package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.validator.EpubValidator
import com.miquido.parsepub.model.EpubSpineModel
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
    private lateinit var validator: ValidationListeners

    @Before
    fun setup() {
        validator = EpubValidator()
        document = documentBuilder.parse(File(OPF_TEST_FILE_PATH))
        spineModel = parser.parse(document, validator.getSpineListeners())
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