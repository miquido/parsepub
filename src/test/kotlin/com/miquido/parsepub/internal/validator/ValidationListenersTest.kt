package com.miquido.parsepub.internal.validator

import com.miquido.parsepub.epubparser.EpubParser
import com.miquido.parsepub.epubvalidator.ValidationListeners
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class ValidationListenersTest {

    private lateinit var tmpDirPath: String
    private var validationListener = mock<ValidationListeners>()
    private val epubParser = EpubParser()

    @Before
    fun setup() {
        setValidationListener()
        tmpDirPath = createTempDir(DIR_NAME_PREFIX, DIR_NAME_SUFFIX).absolutePath
    }

    private fun setValidationListener() {
        epubParser.setValidationListeners {
            setOnManifestMissing { validationListener.onManifestMissing() }
            setOnMetadataMissing { validationListener.onMetadataMissing() }
            setOnSpineMissing { validationListener.onSpineMissing() }
            setOnTableOfContentsMissing { validationListener.onTableOfContentsMissing() }
            setOnAttributeMissing { parentElement, attributeName ->
                validationListener.onAttributeMissing(parentElement, attributeName)
            }
        }
    }

    @Test
    fun `validation listener should be called when epub structure is incorrect`() {
        epubParser.parse(EBOOK_STRUCT_TEST_FILE_PATH, tmpDirPath)
        verify(validationListener).onSpineMissing()
        verify(validationListener).onManifestMissing()
        verify(validationListener).onMetadataMissing()
        verify(validationListener).onTableOfContentsMissing()
    }

    @Test
    fun `onAttributeMissing should be called a specified number of times when epub attributes are missing`() {
        epubParser.parse(EBOOK_ATTR_TEST_FILE_PATH, tmpDirPath)
        verify(validationListener, times(EXPECTED_MISSING_ATTRIBUTES_COUNT))
            .onAttributeMissing(any(), any())
    }

    companion object {
        private const val EBOOK_STRUCT_TEST_FILE_PATH = "src/test/res/epub/test_ebook_structure.zip"
        private const val EBOOK_ATTR_TEST_FILE_PATH = "src/test/res/epub/test_ebook_attr.zip"
        private const val DIR_NAME_PREFIX = "tmp"
        private const val DIR_NAME_SUFFIX = "decompress"
        private const val EXPECTED_MISSING_ATTRIBUTES_COUNT = 12
    }
}