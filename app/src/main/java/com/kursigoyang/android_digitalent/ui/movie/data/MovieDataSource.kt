package com.kursigoyang.android_digitalent.ui.movie.data

import com.kursigoyang.android_digitalent.listener.ResultCallback
import com.kursigoyang.android_digitalent.model.Movie

interface MovieDataSource {

  fun getMovies(callback: ResultCallback<MutableList<Movie>>)
  fun searchMovies(query: String, callback: ResultCallback<MutableList<Movie>>)

}