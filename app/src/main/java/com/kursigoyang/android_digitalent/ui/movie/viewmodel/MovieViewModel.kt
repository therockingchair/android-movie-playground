package com.kursigoyang.android_digitalent.ui.movie.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kursigoyang.android_digitalent.listener.ResultCallback
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.data.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

  internal var movies = MutableLiveData<MutableList<Movie>>()
  internal var error = MutableLiveData<Exception>()

  internal var noFavoriteMovies = MutableLiveData<Boolean>()

  fun loadMovies() {

    movieRepository.getMovies(object : ResultCallback<MutableList<Movie>> {
      override fun onSuccess(data: MutableList<Movie>?) {
        movies.postValue(data)
      }

      override fun onFailure(message: String, e: Exception) {
        error.postValue(e)
      }
    })
  }

  fun searchMovies(query: String) {
    movieRepository.searchMovies(query, object : ResultCallback<MutableList<Movie>> {
      override fun onSuccess(data: MutableList<Movie>?) {
        movies.postValue(data)
      }

      override fun onFailure(message: String, e: Exception) {
        error.postValue(e)
      }

    })
  }

  fun getFavoriteMovies(): LiveData<PagedList<Movie>> = LivePagedListBuilder(movieRepository.getFavoriteMovies()!!, 5).setBoundaryCallback(object : PagedList.BoundaryCallback<Movie>() {
    override fun onZeroItemsLoaded() {
      noFavoriteMovies.postValue(true)
    }

    override fun onItemAtFrontLoaded(itemAtFront: Movie) {
      noFavoriteMovies.postValue(false)
    }
  }).build()

  fun isFavorite(id: Int) = movieRepository.isFavorite(id)

  fun insertFavorite(movie: Movie) {
    movieRepository.insertFavorite(movie)
  }

  fun deleteFavorite(movie: Movie) {
    movieRepository.deleteFavorite(movie)
  }

}