package com.miquido.parsepub.internal.extensions

fun <T> T.validateElementExt(action: (() -> Unit)?): T {
    if (this == null) action?.invoke()
    return this
}