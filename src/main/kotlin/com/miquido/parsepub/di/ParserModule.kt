package com.miquido.parsepub.di

import org.koin.dsl.module.module
import javax.xml.parsers.DocumentBuilderFactory

val parserModule = module {
    single {
        DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()
    }
}