package com.miquido.parsepub

import com.miquido.parsepub.di.parserModule
import org.koin.dsl.module.Module
import org.koin.standalone.StandAloneContext.startKoin

fun main(args: Array<String>) {
    startKoin(diModules)
    System.exit(0)
}

val diModules = listOf(parserModule)