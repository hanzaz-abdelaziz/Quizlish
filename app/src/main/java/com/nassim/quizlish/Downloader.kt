package com.nassim.quizlish

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.JavascriptInterface

class Downloader(private val context: Context) {
    @JavascriptInterface
    fun download(url: String) {
        // Create a DownloadManager.Request
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Download") // Title of the Download Notification
            .setDescription("Downloading image") // Description of the Download Notification
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg") // Storage directory path

        // Get download service and enqueue file
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}
