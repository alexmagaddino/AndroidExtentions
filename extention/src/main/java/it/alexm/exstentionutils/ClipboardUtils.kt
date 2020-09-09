package it.alexm.extentionutils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Created by alexm on 09/02/22
 * */
fun Activity.copyToClipboard(text: String?, label: String = "") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    showToast("$label ${getString(R.string.copied)}")
}