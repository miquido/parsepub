package com.miquido.parsepubsample

import android.app.Activity
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.miquido.parsepubsample.chapter.LocalFileWebViewActivity
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

fun Context?.openFileInWebView(path: String) {
    this?.startActivity(LocalFileWebViewActivity.newIntent(this, path))
}