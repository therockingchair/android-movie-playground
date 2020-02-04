package com.kursigoyang.android_digitalent.utils

import android.content.Context
import com.kursigoyang.android_digitalent.R
import java.lang.Exception
import java.net.UnknownHostException

object ErrorHelper {

  fun message(context: Context,e : Exception) : String {
    return when(e) {
      is UnknownHostException -> context.getString(R.string.no_connection)
      else -> context.getString(R.string.please_try_again)
    }
  }

  class DefaultException : Exception()
}