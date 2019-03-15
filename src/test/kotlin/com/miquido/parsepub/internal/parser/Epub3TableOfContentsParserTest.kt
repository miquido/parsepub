package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.internal.constants.EpubConstants.EPUB_MAJOR_VERSION_3
import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.internal.parser.toc.TableOfContentParserFactory
import com.miquido.parsepub.internal.validator.TestEpubValidator
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class Epub3TableOfContentsParserTest {

    private val parserFactory: TableOfContentParserFactory by lazy { ParserModuleProvider.epubTableOfContentsParserFactory }
    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }

    private lateinit var tocDocument: Document
    private lateinit var tocModel: EpubTableOfContentsModel

    @Before
    fun setup() {
        tocDocument = documentBuilder.parse(File(NCX_TEST_FILE_PATH))
        tocModel = parserFactory.getTableOfContentsParser(EPUB_MAJOR_VERSION_3).parse(tocDocument, TestEpubValidator())
    }

    @Test
    fun `table of contents parser should parse main entries`() {
        assertThat(tocModel.tableOfContents).hasSize(EXPECTED_TOC_MAIN_SIZE)
        assertThat(tocModel.tableOfContents).extracting(LABEL_FIELD_NAME)
            .contains(*EXPECTED_TOC_MAIN_ELEMENTS.map { it.label }.toTypedArray())
        assertThat(tocModel.tableOfContents).extracting(SOURCE_FIELD_NAME)
            .contains(*EXPECTED_TOC_MAIN_ELEMENTS.map { it.location }.toTypedArray())
    }

    @Test
    fun `table of contents parser should parse nested entries`() {
        assertThat(tocModel.tableOfContents.last().subItems)
            .hasSize(EXPECTED_TOC_NESTED_SIZE).containsExactlyElementsOf(EXPECTED_TOC_LAST_ITEM_NESTED_ELEMENT)
    }

    private companion object {
        private const val NCX_TEST_FILE_PATH = "src/test/res/toc/toc.xhtml"
        private const val EXPECTED_TOC_MAIN_SIZE = 3
        private const val EXPECTED_TOC_NESTED_SIZE = 1

        private const val LABEL_FIELD_NAME = "label"
        private const val SOURCE_FIELD_NAME = "location"

        private val EXPECTED_TOC_LAST_ITEM_NESTED_ELEMENT = listOf(
            NavigationItemModel(null, "sub_AZARDI", "sub-s003.xhtml", emptyList())
        )
        private val EXPECTED_TOC_MAIN_ELEMENTS = listOf(
            NavigationItemModel(null, "Middle School Maths", "s001.xhtml", emptyList()),
            NavigationItemModel(null, "Copyright", "s002.xhtml", emptyList()),
            NavigationItemModel(null, "AZARDI", "s003.xhtml", emptyList())
        )
    }
}