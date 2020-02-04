package com.kursigoyang.android_digitalent.provider

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

  const val AUTHORITY = "com.kursigoyang.android_digitalent"
  const val SCHEME = "content"

  class MovieColumns : BaseColumns {

    companion object {
      const val TABLE_NAME = "movie"

      val CONTENT_URI = Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME)
        .build()
    }
  }
}