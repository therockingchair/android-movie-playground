package kursigoyang.com.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

  const val AUTHORITY = "com.kursigoyang.android_digitalent"
  const val SCHEME = "content"

  class MovieColumns : BaseColumns {

    companion object {
      const val TABLE_NAME = "movie"
      const val ID = "id"
      const val TITLE = "title"

      val CONTENT_URI = Uri.Builder().scheme(SCHEME).authority(AUTHORITY)
        .appendPath(TABLE_NAME).build()
    }
  }
}