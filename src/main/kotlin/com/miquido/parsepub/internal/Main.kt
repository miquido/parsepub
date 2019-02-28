package com.miquido.parsepub.internal

import com.miquido.parsepub.internal.di.parserModule
import org.koin.standalone.StandAloneContext.startKoin

fun main() {
    startKoin(diModules)
    System.exit(0)
}

internal val diModules = listOf(parserModule)