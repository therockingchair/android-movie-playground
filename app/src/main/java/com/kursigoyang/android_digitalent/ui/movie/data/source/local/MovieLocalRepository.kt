package com.kursigoyang.android_digitalent.ui.movie.data.source.local

import android.util.Log
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MovieLocalRepository(database: BaseRoomDatabase) {

  private var movieDao = database.movieDao()

  fun getFavoriteMovies() = movieDao.getDataPagination()

  fun isFavorite(id: Int): Movie = runBlocking {
    movieDao.isFavorite(id)
  }

  fun insertFavorite(movie: Movie) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        movieDao.insert(movie)
      } catch (e: Exception) {
        Log.d("error", e.message)
      }
    }
  }

  fun deleteFavorite(movie: Movie) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        movieDao.deleteById(movie.id)
      } catch (e: Exception) {
        Log.d("error", e.message)
      }
    }
  }
}