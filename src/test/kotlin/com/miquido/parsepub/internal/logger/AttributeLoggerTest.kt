package com.miquido.parsepub.internal.logger

import com.miquido.parsepub.epubparser.EpubParser
import com.miquido.parsepub.epublogger.AttributeLogger
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class AttributeLoggerTest {

    private lateinit var tmpDirPath: String
    private val attributeLogger = mock<AttributeLogger>()
    private val epubParser = EpubParser()

    @Before
    fun setup() {
        tmpDirPath = createTempDir(DIR_NAME_PREFIX, DIR_NAME_SUFFIX).absolutePath
        setMissingAttributeLogger()
    }

    private fun setMissingAttributeLogger() {
        epubParser.setMissingAttributeLogger {
            setOnAttributeLogger { parentName: String, attributeName: String ->
                attributeLogger.logMissingAttribute(parentName, attributeName)
            }
        }
    }

    @Test
    fun `logger should be called a specified number of times when epub attributes are missing`() {
        epubParser.parse(EBOOK_FILE_PATH, tmpDirPath)
        verify(attributeLogger, times(EXPECTED_LOGS_COUNT)).logMissingAttribute(any(), any())
    }

    companion object {
        private const val EBOOK_FILE_PATH = "src/test/res/epub/test_ebook_attr.zip"
        private const val DIR_NAME_PREFIX = "tmp"
        private const val DIR_NAME_SUFFIX = "decompress"
        private const val EXPECTED_LOGS_COUNT = 11
    }
}