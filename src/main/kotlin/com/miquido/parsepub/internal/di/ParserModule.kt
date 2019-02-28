package com.miquido.parsepub.internal.di

import org.koin.dsl.module.module
import javax.xml.parsers.DocumentBuilderFactory

internal val parserModule = module {
    single {
        DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()
    }
}