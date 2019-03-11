package com.miquido.parsepub.internal.extensions

fun <T> T.orValidationError(action: (() -> Unit)?): T {
    if (this == null) action?.invoke()
    return this
}