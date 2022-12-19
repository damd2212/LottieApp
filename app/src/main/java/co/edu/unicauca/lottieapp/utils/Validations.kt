package co.edu.unicauca.lottieapp.utils

import android.text.TextUtils
import android.util.Patterns

fun String.isValidEmail():Boolean{
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches() && this.contains("@unicauca.edu.co")
}

fun String.isValidPassword():Boolean{
    return this.length >= 8
}