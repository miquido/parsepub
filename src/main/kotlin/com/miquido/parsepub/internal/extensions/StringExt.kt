package com.miquido.parsepub.internal.extensions

internal fun String.orNullIfEmpty() = if (this.isBlank()) null else this