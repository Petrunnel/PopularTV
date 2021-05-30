package com.example.notes.populartv.utilits

import android.widget.Toast
import java.lang.Exception

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun showYear(string: String): String {
    // "2021-04-23"

    return try {
        val inputDateFormat = java.text.SimpleDateFormat("yyyy-MM-dd")
        val date = inputDateFormat.parse(string)
        val outputDateFormat = java.text.SimpleDateFormat("yyyy")
        outputDateFormat.format(date)
    } catch (exception: Exception) {
        "null"
    }
}