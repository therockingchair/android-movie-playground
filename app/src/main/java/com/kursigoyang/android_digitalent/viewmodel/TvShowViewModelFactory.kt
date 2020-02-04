package com.kursigoyang.android_digitalent.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.ui.tvshow.viewmodel.TvShowViewModel
import java.lang.reflect.InvocationTargetException

open class TvShowViewModelFactory(private val baseRoomDatabase: BaseRoomDatabase?) : ViewModelProvider.NewInstanceFactory() {

  companion object {

    private var instance: TvShowViewModelFactory? = null

    /**
     * Retrieve a singleton instance of MovieViewModelFactory.
     *
     * @return A valid [TvShowViewModelFactory]
     */
    fun getInstance(baseRoomDatabase: BaseRoomDatabase?): TvShowViewModelFactory {
      if (instance == null) {
        instance = TvShowViewModelFactory(baseRoomDatabase)
      }
      return instance as TvShowViewModelFactory
    }
  }

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (TvShowViewModel::class.java.isAssignableFrom(modelClass)) {

      try {
        return TvShowViewModel(baseRoomDatabase) as (T)
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