package com.miquido.parsepub.internal.validator

import com.miquido.parsepub.epubparser.EpubParser
import com.miquido.parsepub.epubvalidator.ValidationListener
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class ValidationListenerTest {

    private lateinit var tmpDirPath: String
    private var validationListener = mock<ValidationListener>()
    private val epubParser = EpubParser()

    @Before
    fun setup() {
        setValidationListener()
        tmpDirPath = createTempDir(DIR_NAME_PREFIX, DIR_NAME_SUFFIX).absolutePath
    }

    private fun setValidationListener() {
        epubParser.setValidationListeners {
            setOnManifestMissing {
                validationListener.onManifestMissing()
            }
            setOnMetadataMissing {
                validationListener.onMetadataMissing()
            }
            setOnSpineMissing {
                validationListener.onSpineMissing()
            }
            setOnTableOfContentMissing {
                validationListener.onTableOfContentsMissing()
            }
        }
    }

    @Test
    fun `validation listener should be called when epub structure is incorrect`() {
        epubParser.parse(
            EBOOK_FILE_PATH,
            tmpDirPath
        )
        verify(validationListener).onSpineMissing()
        verify(validationListener).onManifestMissing()
        verify(validationListener).onMetadataMissing()
        verify(validationListener).onTableOfContentsMissing()
    }

    companion object {
        private const val EBOOK_FILE_PATH = "src/test/res/epub/test_ebook_structure.zip"
        private const val DIR_NAME_PREFIX = "tmp"
        private const val DIR_NAME_SUFFIX = "decompress"
    }
}