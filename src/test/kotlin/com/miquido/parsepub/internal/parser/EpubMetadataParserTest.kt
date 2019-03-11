package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.validator.EpubValidator
import com.miquido.parsepub.model.EpubMetadataModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class EpubMetadataParserTest {

    private val metadataParser: EpubMetadataParser by lazy { ParserModuleProvider.epubMetadataParser }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }
    private lateinit var document: Document
    private lateinit var metadataModel: EpubMetadataModel
    private lateinit var validator: ValidationListeners

    @Before
    fun setup() {
        validator = EpubValidator()
        document = documentBuilder.parse(File(OPF_TEST_FILE_PATH))
        metadataModel = metadataParser.parse(document, validator.getMetadataListeners())
    }

    @Test
    fun `metadata parser should parse all model fields`() {
        assertThat(metadataModel.creators).containsExactlyElementsOf(EXPECTED_CREATORS_VALUES)
        assertThat(metadataModel.languages).containsExactlyElementsOf(EXPECTED_LANGUAGES_VALUES)
        assertThat(metadataModel.contributors).containsExactlyElementsOf(EXPECTED_CONTRIBUTORS_VALUES)
        assertThat(metadataModel.subjects).containsExactlyElementsOf(EXPECTED_SUBJECTS_VALUES)
        assertThat(metadataModel.sources).containsExactlyElementsOf(EXPECTED_SOURCES_VALUES)
        assertThat(metadataModel.title).isNotBlank().isEqualTo(EXPECTED_TITLE_VALUE)
        assertThat(metadataModel.description).isNotBlank().isEqualTo(EXPECTED_DESCRIPTION_VALUE)
        assertThat(metadataModel.rights).isNotBlank().isEqualTo(EXPECTED_RIGHTS_VALUE)
        assertThat(metadataModel.coverage).isNotBlank().isEqualTo(EXPECTED_COVERAGE_VALUE)
        assertThat(metadataModel.publisher).isNotBlank().isEqualTo(EXPECTED_PUBLISHER_VALUE)
        assertThat(metadataModel.relation).isNotBlank().isEqualTo(EXPECTED_RELATION_VALUE)
        assertThat(metadataModel.date).isNotBlank().isEqualTo(EXPECTED_DATE_VALUE)
        assertThat(metadataModel.id).isNotBlank().isEqualTo(EXPECTED_ID_VALUE)
    }

    companion object {
        private const val OPF_TEST_FILE_PATH = "src/test/res/opf/book.opf"

        private val EXPECTED_CREATORS_VALUES = listOf("Bertrand Russell", "Second Creator")
        private val EXPECTED_LANGUAGES_VALUES = listOf("en", "pl")
        private val EXPECTED_CONTRIBUTORS_VALUES = listOf("Contributor One", "Contributor Two")
        private val EXPECTED_SUBJECTS_VALUES = listOf("Subject 1", "Subject 2")
        private val EXPECTED_SOURCES_VALUES = listOf("Source 1", "Source 2", "Source 3")

        private const val EXPECTED_ID_VALUE = "http://theproblemofphilosophy.pressbooks.com"
        private const val EXPECTED_DATE_VALUE = "2013-12-12"
        private const val EXPECTED_RELATION_VALUE = "RelationID"
        private const val EXPECTED_PUBLISHER_VALUE = "PresssBooks.com"
        private const val EXPECTED_COVERAGE_VALUE = "United States"
        private const val EXPECTED_RIGHTS_VALUE = "Copyright Public Domain by Public Domain"
        private const val EXPECTED_DESCRIPTION_VALUE =
            "The Problems of Philosophy (1912) is one of Bertrand Russell's attempts to create ..."
        private const val EXPECTED_TITLE_VALUE = "The Problems of Philosophy"
    }
}