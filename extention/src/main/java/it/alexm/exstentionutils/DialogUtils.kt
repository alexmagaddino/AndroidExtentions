package it.alexm.extentionutils

import android.app.AlertDialog
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * Created by alexm on 09/02/22
 * */
fun Fragment.alertDialog(
    title: String,
    subtitle: String,
    @StringRes confirmButton: Int = android.R.string.ok,
    @StringRes cancelButton: Int = R.string.annulla,
    cancelAction: () -> Unit? = {},
    action: () -> Unit
) {
    dialog {
        this.setTitle(title)
            .setMessage(subtitle)
            .setNegativeButton(cancelButton) { dialog, _ ->
                dialog.dismiss()
                cancelAction.invoke()
            }
            .setPositiveButton(confirmButton) { _, _ ->
                action.invoke()
            }
    }
}

inline fun Fragment.dialog(block: AlertDialog.Builder.() -> Unit): AlertDialog {
    val dialog = AlertDialog.Builder(requireContext(), android.R.style.Theme_Material_Dialog_Alert)
    block.invoke(dialog)
    return dialog.show()
}