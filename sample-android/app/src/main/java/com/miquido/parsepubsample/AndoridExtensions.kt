package com.miquido.parsepubsample

import android.content.Context
import java.io.File

fun Context.copyFileFromAssets(assetName: String, outputDir: String): String {
    val assetInputStream = assets.open(assetName)
    val outputFile = File("$outputDir/$assetName")
    assetInputStream.use { input ->
        outputFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return outputFile.path
}