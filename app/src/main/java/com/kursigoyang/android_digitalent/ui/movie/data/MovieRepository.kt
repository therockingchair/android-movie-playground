package com.kursigoyang.android_digitalent.ui.movie.data

import com.kursigoyang.android_digitalent.listener.ResultCallback
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.data.source.local.MovieLocalRepository
import com.kursigoyang.android_digitalent.ui.movie.data.source.remote.MovieRemoteRepository

class MovieRepository(private val remoteRepository: MovieRemoteRepository, private val localRepository: MovieLocalRepository? = null) : MovieDataSource {

  override fun getMovies(callback: ResultCallback<MutableList<Movie>>) {
    remoteRepository.getMovies(callback)
  }

  override fun searchMovies(query: String, callback: ResultCallback<MutableList<Movie>>) {
    remoteRepository.searchMovies(query, callback)
  }

  fun getFavoriteMovies() = localRepository?.getFavoriteMovies()

  fun insertFavorite(movie: Movie) {
    localRepository?.insertFavorite(movie)
  }

  fun deleteFavorite(movie: Movie) {
    localRepository?.deleteFavorite(movie)
  }

  fun isFavorite(id: Int) = localRepository?.isFavorite(id)

}
