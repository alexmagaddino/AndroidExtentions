package it.alexm.extentionutils

import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Environment
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File

/**
 * Created by alexm on 09/02/22
 * */
fun Activity.browser(url: String?, requestInt: Int = -1) {
    if (url != null) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            if (requestInt > -1) {
                startActivityForResult(browserIntent, requestInt)
            } else {
                startActivity(browserIntent)
            }
        } catch (e: ActivityNotFoundException) {
            startActivityForResult(
                Intent.createChooser(browserIntent, "Apri con..."), requestInt
            )
        }
    }
}

fun Activity.dial(phoneNumber: String, requestInt: Int = -1) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse(if (!phoneNumber.contains("tel:")) "tel:$phoneNumber" else phoneNumber)
    }
    if (intent.resolveActivity(packageManager) != null) {
        if (requestInt > -1) {
            startActivityForResult(intent, requestInt)
        } else {
            startActivity(intent)
        }
    }
}

fun Activity.whatsapp(phoneNumber: String, requestInt: Int = -1): Boolean {
    return try {
        browser(
            if (!phoneNumber.contains("https://wa.me/")) "https://wa.me/$phoneNumber" else phoneNumber,
            requestInt
        )
        true
    } catch (e: java.lang.Exception) {
        false
    }
}

fun Activity.share(chooserText: String, subject: String, text: String) {
    startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }, chooserText))
}

fun Activity.email(to: String, subject: String = "", body: String = "") {

    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, to)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    startActivity(emailIntent)
}

fun Activity.openPdf(pdf: File) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                FileProvider.getUriForFile(
                    this@openPdf,
                    applicationContext.packageName + ".provider", pdf
                ),
                "application/pdf"
            )
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        })
    } catch (e: ActivityNotFoundException) {
        showToast(R.string.no_activity_to_open_pdf, Toast.LENGTH_LONG)
    }
}

fun Activity.openPdf(uriPdf: Uri) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uriPdf, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        })
    } catch (e: ActivityNotFoundException) {
        showToast(R.string.no_activity_to_open_pdf, Toast.LENGTH_LONG)
    }
}