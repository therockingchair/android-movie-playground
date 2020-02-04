package com.kursigoyang.android_digitalent.ui.tvshow.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.database.dao.FavoriteTvShowDao
import com.kursigoyang.android_digitalent.model.TvShow
import com.kursigoyang.android_digitalent.utils.ErrorHelper
import com.kursigoyang.android_digitalent.utils.EspressoIdlingResource
import com.kursigoyang.android_digitalent.utils.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowViewModel(baseRoomDatabase: BaseRoomDatabase? = null) : ViewModel() {

  internal var tvshows = MutableLiveData<MutableList<TvShow>>()
  internal var error = MutableLiveData<Exception>()
  private var favoriteTvShowDao: FavoriteTvShowDao? = null

  init {
    if (baseRoomDatabase != null) {
      favoriteTvShowDao = baseRoomDatabase.favoriteTvShowDao()
    }
  }

  fun loadTvShow() {
    EspressoIdlingResource.increment()
    CoroutineScope(Dispatchers.IO).launch {

      try {
        val response = ApiService.instance.getTvShows().await()
        if (response.isSuccessful) {
          tvshows.postValue(response.body()?.result)
        } else {
          error.postValue(ErrorHelper.DefaultException())
        }
        EspressoIdlingResource.increment()
      } catch (e: Exception) {
        EspressoIdlingResource.increment()
        error.postValue(e)
        e.printStackTrace()
      }
    }
  }

  fun searchTvShow(query: String) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val response = ApiService.instance.searchTvShow(query).await()
        if (response.isSuccessful) {
          tvshows.postValue(response.body()?.result)
        } else {
          error.postValue(ErrorHelper.DefaultException())
        }
      } catch (e: Exception) {
        error.postValue(e)
        e.printStackTrace()
      }
    }
  }

  fun getFavoritesTvShow(): LiveData<MutableList<TvShow>>? = favoriteTvShowDao?.getData()

  fun insert(tvShow: TvShow) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        favoriteTvShowDao?.insert(tvShow)
      } catch (e: Exception) {
        Log.d("error", e.message)
      }
    }
  }

  fun delete(tvShow: TvShow) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        favoriteTvShowDao?.deleteById(tvShow.id)
      } catch (e: Exception) {
        Log.d("error", e.message)
      }
    }
  }
}