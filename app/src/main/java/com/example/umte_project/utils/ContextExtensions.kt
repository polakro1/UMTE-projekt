package com.example.umte_project.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null

}

fun Context.findFragmentActivity(): FragmentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is FragmentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}