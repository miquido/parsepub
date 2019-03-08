package com.miquido.parsepubsample.chapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.miquido.parsepubsample.R
import kotlinx.android.synthetic.main.activity_local_file_web_view.*
import java.io.File


class LocalFileWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_file_web_view)

        val path = intent.getStringExtra(EXTRA_LOCAL_FILE_PATH)
        loadFile(path) {
            runOnUiThread {
                progressBar.visibility = View.GONE
                webView.loadData(it, MIME_TYPE_TEXT_HTML, Charsets.UTF_8.name())
            }
        }
    }

    private fun loadFile(path: String, onComplete: (String) -> Unit) {
        Thread {
            File(path).inputStream().use {
                val buffer = ByteArray(it.available())
                it.read(buffer)
                onComplete(String(buffer))
            }
        }.start()
    }

    companion object {
        private const val EXTRA_LOCAL_FILE_PATH = "LOCAL_FILE_PATH"
        private const val MIME_TYPE_TEXT_HTML = "text/html"

        fun newIntent(context: Context, localFilePath: String) =
            Intent(context, LocalFileWebViewActivity::class.java).apply {
                putExtra(EXTRA_LOCAL_FILE_PATH, localFilePath)
            }
    }
}