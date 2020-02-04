package com.kursigoyang.android_digitalent.ui.movie.data.source.remote

import com.kursigoyang.android_digitalent.listener.ResultCallback
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.utils.ErrorHelper
import com.kursigoyang.android_digitalent.utils.EspressoIdlingResource
import com.kursigoyang.android_digitalent.utils.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieRemoteRepository {

  fun getMovies(callback: ResultCallback<MutableList<Movie>>) {
    EspressoIdlingResource.increment()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val response = ApiService.instance.getMovies().await()
        if (response.isSuccessful) {
          callback.onSuccess(response.body()?.result)
        } else {
          callback.onFailure("", ErrorHelper.DefaultException())
        }
        EspressoIdlingResource.decrement()
      } catch (e: Exception) {
        EspressoIdlingResource.decrement()
        callback.onFailure("", e)
        e.printStackTrace()
      }
    }
  }

  fun searchMovies(query: String, callback: ResultCallback<MutableList<Movie>>) {
    EspressoIdlingResource.increment()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val response = ApiService.instance.searchMovies(query).await()
        if (response.isSuccessful) {
          callback.onSuccess(response.body()?.result)
        } else {
          callback.onFailure("", ErrorHelper.DefaultException())
        }
        EspressoIdlingResource.decrement()
      } catch (e: Exception) {
        EspressoIdlingResource.decrement()
        callback.onFailure("", ErrorHelper.DefaultException())
        e.printStackTrace()
      }
    }
  }
}