package com.kursigoyang.android_digitalent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kursigoyang.android_digitalent.ui.movie.data.MovieRepository
import com.kursigoyang.android_digitalent.ui.movie.viewmodel.MovieViewModel
import java.lang.reflect.InvocationTargetException

open class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

  companion object {

    private var instance: MovieViewModelFactory? = null

    /**
     * Retrieve a singleton instance of MovieViewModelFactory.
     *
     * @return A valid [MovieViewModelFactory]
     */
    fun getInstance(repository: MovieRepository): MovieViewModelFactory {
      if (instance == null) {
        instance = MovieViewModelFactory(repository)
      }
      return instance as MovieViewModelFactory
    }
  }

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (MovieViewModel::class.java.isAssignableFrom(modelClass)) {

      try {
        return MovieViewModel(repository) as (T)
      } catch (e: NoSuchMethodException) {
        throw RuntimeException("Cannot create an instance of $modelClass", e)
      } catch (e: IllegalAccessException) {
        throw RuntimeException("Cannot create an instance of $modelClass", e)
      } catch (e: InstantiationException) {
        throw RuntimeException("Cannot create an instance of $modelClass", e)
      } catch (e: InvocationTargetException) {
        throw RuntimeException("Cannot create an instance of $modelClass", e)
      }

    }
    return super.create(modelClass)
  }
}