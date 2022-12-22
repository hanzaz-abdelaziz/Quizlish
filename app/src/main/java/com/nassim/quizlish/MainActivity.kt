package com.nassim.quizlish

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.web_view)
        webView.settings.javaScriptEnabled = true

        // Inject the Downloader object into the WebView
        webView.addJavascriptInterface(Downloader(this), "downloader")

        //Make Popup
        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                // Create an AlertDialog to display the alert message
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage(message)
                    .setPositiveButton("OK") { _, _ -> result?.confirm() }
                val dialog = builder.create()
                dialog.show()
                return true
            }
        }

        //Download images files
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            // Check if the MIME type is an image
            if (mimetype.startsWith("image/")) {
                // Create a DownloadManager.Request
                val request = DownloadManager.Request(Uri.parse(url))
                    .setTitle("Download") // Title of the Download Notification
                    .setDescription("Downloading image") // Description of the Download Notification
                    .setMimeType(mimetype)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg") // Storage directory path

                // Get download service and enqueue file
                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
            }
        }

        //Download any files Example Pdf
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            // Create a DownloadManager.Request
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Download") // Title of the Download Notification
                .setDescription("Downloading file") // Description of the Download Notification
                .setMimeType(mimetype)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "file.pdf") // Storage directory path

            // Get download service and enqueue file
            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
        }



    }
}