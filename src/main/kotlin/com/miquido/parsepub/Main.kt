package com.miquido.parsepub

import org.koin.dsl.module.Module
import org.koin.standalone.StandAloneContext.startKoin

fun main(args: Array<String>) {
    startKoin(diModules)
    System.exit(0)
}

val diModules = listOf<Module>()