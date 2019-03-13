package com.miquido.parsepub.internal.parser

import com.miquido.parsepub.internal.di.ParserModuleProvider
import com.miquido.parsepub.model.EpubTableOfContentsModel
import com.miquido.parsepub.model.NavigationItemModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilder

class TableOfContentsParserTest {

    private val parser: EpubTableOfContentsParser by lazy { ParserModuleProvider.epubTableOfContentsParser }

    private val documentBuilder: DocumentBuilder by lazy { ParserModuleProvider.documentBuilder }
    private lateinit var ncxDocument: Document
    private lateinit var tocModel: EpubTableOfContentsModel

    @Before
    fun setup() {
        ncxDocument = documentBuilder.parse(File(NCX_TEST_FILE_PATH))
        tocModel = parser.parse(ncxDocument)
    }

    @Test
    fun `table of contents parser should parse main entries`() {
        assertThat(tocModel.tableOfContents).hasSize(EXPECTED_TOC_MAIN_SIZE)
        assertThat(tocModel.tableOfContents).extracting(ID_FIELD_NAME)
            .contains(*EXPECTED_TOC_MAIN_ELEMENTS.map { it.id }.toTypedArray())
        assertThat(tocModel.tableOfContents).extracting(LABEL_FIELD_NAME)
            .contains(*EXPECTED_TOC_MAIN_ELEMENTS.map { it.label }.toTypedArray())
        assertThat(tocModel.tableOfContents).extracting(SOURCE_FIELD_NAME)
            .contains(*EXPECTED_TOC_MAIN_ELEMENTS.map { it.location }.toTypedArray())
    }

    @Test
    fun `table of contents parser should parse nested entries`() {
        assertThat(tocModel.tableOfContents.first().subItems?.first()?.subItems?.first()?.subItems)
            .hasSize(EXPECTED_TOC_NESTED_SIZE).containsExactlyElementsOf(EXPECTED_TOC_FIRST_ITEM_NESTED_ELEMENT)
    }

    private companion object {
        private const val NCX_TEST_FILE_PATH = "src/test/res/ncx/toc.ncx"
        private const val EXPECTED_TOC_MAIN_SIZE = 2
        private const val EXPECTED_TOC_NESTED_SIZE = 2

        private const val ID_FIELD_NAME = "id"
        private const val LABEL_FIELD_NAME = "label"
        private const val SOURCE_FIELD_NAME = "location"

        private val EXPECTED_TOC_FIRST_ITEM_NESTED_ELEMENT = listOf(
            NavigationItemModel("ch_1_1_1", "Chapter 1.1.1", "content.html#ch_1_1_1", emptyList()),
            NavigationItemModel("ch_1_1_2", "Chapter 1.1.2", "content.html#ch_1_1_2", emptyList())
        )
        private val EXPECTED_TOC_MAIN_ELEMENTS = listOf(
            NavigationItemModel("ch1", "Chapter 1", "content.html#ch_1", emptyList()),
            NavigationItemModel("ncx-2", "Chapter 2", "content.html#ch_2", emptyList())
        )
    }
}