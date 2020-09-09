package it.alexm.extentionutils

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by alexm on 09/02/22
 * */
fun Activity.showToast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    try {
        Toast.makeText(this, id, length).show()
    } catch (e: Exception) {
        //nothing
    }
}

fun Fragment.showToast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    activity?.showToast(id, length)
}

fun Activity.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    try {
        Toast.makeText(this, msg, length).show()
    } catch (e: Exception) {
        //nothing
    }
}

fun Fragment.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    activity?.showToast(msg, length)
}