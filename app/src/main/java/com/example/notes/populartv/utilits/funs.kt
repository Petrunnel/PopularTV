package com.example.notes.populartv.utilits

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

fun showToast(message:String){
    Toast.makeText(APP_ACTIVITY,message, Toast.LENGTH_SHORT).show()
}

fun showYear(string: String): String? {
    // "2021-04-23"

    val inputDateFormat = java.text.SimpleDateFormat("yyyy-MM-dd")
    val date = inputDateFormat.parse(string)
    val outputDateFormat = java.text.SimpleDateFormat("yyyy")
    return outputDateFormat.format(date)
}