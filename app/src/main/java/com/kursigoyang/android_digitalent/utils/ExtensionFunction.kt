package com.kursigoyang.android_digitalent.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast

fun Context.toast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}