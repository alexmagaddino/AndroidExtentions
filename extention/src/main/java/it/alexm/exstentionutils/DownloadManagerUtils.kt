package it.alexm.extentionutils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment

/**
 * Created by alexm on 09/02/22
 * */
fun Context.downloadManager(
    fileName: String,
    url: String,
    onComplete: (Uri) -> Unit
): BroadcastReceiver {

    val downloadRequest =
        DownloadManager.Request(
            Uri.parse(url)
        )
    downloadRequest.setTitle(fileName)
        .setDescription("$fileName is downloading...")
        .setDestinationInExternalFilesDir(
            this,
            Environment.DIRECTORY_DOWNLOADS,
            "$fileName.pdf"
        )
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

    val location = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadId = location.enqueue(downloadRequest)

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) onComplete.invoke(location.getUriForDownloadedFile(downloadId))
        }
    }

    registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    return receiver
}