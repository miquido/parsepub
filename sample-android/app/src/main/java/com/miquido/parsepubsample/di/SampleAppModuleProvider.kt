package com.miquido.parsepubsample.di

import com.miquido.parsepub.epubvalidator.ValidationListener
import com.miquido.parsepubsample.internal.ValidationListeners

internal interface SampleAppModule {
    val validationListener: ValidationListener
}

internal class SampleAppModuleProvider: SampleAppModule {
    override val validationListener: ValidationListener by lazy { ValidationListeners() }
}