package it.alexm.extentionutils

import android.app.Activity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 * Created by alexm on 09/02/22
 * Nuove funzioni per nascondere o richiamare la soft keyboard
 * */
fun Activity.setSoftKeyboardVisibility(toShow: Boolean?) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        currentFocus?.also {
            if (toShow == true)
                showSoftInput(it, InputMethodManager.SHOW_FORCED)
            else
                hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}

fun Activity.setButtonVisibleWithSoftKeyboard(visibleWithKeyboard: Boolean) {
    window.setSoftInputMode(
        if (visibleWithKeyboard) WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        else
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    )
}