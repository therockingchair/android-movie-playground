package com.kursigoyang.android_digitalent.utils

import android.database.Cursor
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.provider.DatabaseContract

object MapCursorHelper {

  fun mapCursorToArrayList(cursor: Cursor): MutableList<Movie> {
    val movieList = mutableListOf<Movie>()

    while (cursor.moveToNext()) {
      val id = cursor.getInt(cursor.getColumnIndexOrThrow(Movie.ID))
      val title = cursor.getString(cursor.getColumnIndexOrThrow(Movie.TITLE))

      movieList.add(Movie().apply {
        this.id = id
        this.title = title
      })
    }

    return movieList
  }
}