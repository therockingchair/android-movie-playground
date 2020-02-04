package com.kursigoyang.android_digitalent.utils

import android.database.Cursor
import com.kursigoyang.android_digitalent.model.Movie

object MapHelper {

  fun cursorToMovie(cursor: Cursor): Movie {
    val id = cursor.getInt(cursor.getColumnIndexOrThrow(Movie.ID))
    val title = cursor.getString(cursor.getColumnIndexOrThrow(Movie.TITLE))
    val overview = cursor.getString(cursor.getColumnIndexOrThrow(Movie.OVERVIEW))
    val tagline = cursor.getString(cursor.getColumnIndexOrThrow(Movie.TAGLINE))
    val releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(Movie.RELEASE_DATE))
    val posterPath = cursor.getString(cursor.getColumnIndexOrThrow(Movie.POSTER_PATH))
    val backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(Movie.BACKDROP_PATH))
    val runtime = cursor.getString(cursor.getColumnIndexOrThrow(Movie.RUNTIME))
    val budget = cursor.getString(cursor.getColumnIndexOrThrow(Movie.BUDGET))
    val revenue = cursor.getString(cursor.getColumnIndexOrThrow(Movie.REVENUE))
    val voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow(Movie.VOTE_AVERAGE))
    val voteCount = cursor.getInt(cursor.getColumnIndexOrThrow(Movie.VOTE_COUNT))


    val movie = Movie().apply {
      this.id = id
      this.title = title
      this.overview = overview
      this.tagline = tagline
      this.releaseDate = releaseDate
      this.posterPath = posterPath
      this.backdropPath = backdropPath
      this.runtime = runtime
      this.budget = budget
      this.revenue = revenue
      this.voteAverage = voteAverage
      this.voteCount = voteCount
    }
    return movie
  }
}