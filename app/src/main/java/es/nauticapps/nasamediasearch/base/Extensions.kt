package es.nauticapps.nasamediasearch.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

//Keyboard
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.prettyDate(): String {
    return try {
        val rawFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val prettyFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = rawFormat.parse(this)
        val result = prettyFormat.format(date)
        result

    }
    catch (e: Exception) {
        this
    }
}