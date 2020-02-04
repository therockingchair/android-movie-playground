package com.kursigoyang.android_digitalent.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.ui.movie.data.MovieRepository
import com.kursigoyang.android_digitalent.ui.movie.data.source.local.MovieLocalRepository
import com.kursigoyang.android_digitalent.ui.movie.data.source.remote.MovieRemoteRepository
import com.kursigoyang.android_digitalent.ui.movie.viewmodel.MovieViewModel
import com.kursigoyang.android_digitalent.ui.tvshow.viewmodel.TvShowViewModel
import com.kursigoyang.android_digitalent.viewmodel.MovieViewModelFactory
import com.kursigoyang.android_digitalent.viewmodel.TvShowViewModelFactory

object Injection {

  fun provideMovieViewModel(activity: AppCompatActivity): MovieViewModel {
    val viewModelFactory = provideMovieViewModelFactory(activity)
    return ViewModelProviders.of(activity, viewModelFactory).get(MovieViewModel::class.java)
  }

  fun provideMovieViewModel(context: Context, fragment: Fragment): MovieViewModel {
    val viewModelFactory = provideMovieViewModelFactory(context)
    return ViewModelProviders.of(fragment, viewModelFactory).get(MovieViewModel::class.java)
  }

  private fun provideMovieViewModelFactory(context: Context): MovieViewModelFactory {
    return MovieViewModelFactory.getInstance(provideMovieRepository(context))
  }

  private fun provideMovieRepository(context: Context): MovieRepository {
    return MovieRepository(MovieRemoteRepository(), MovieLocalRepository(BaseRoomDatabase.getDatabase(context)))
  }

  //  Tv Show
  fun provideTvShowViewModel(baseRoomDatabase: BaseRoomDatabase, activity: AppCompatActivity): TvShowViewModel {
    val viewModelFactory = provideTvShowViewModelFactory(baseRoomDatabase)
    return ViewModelProviders.of(activity, viewModelFactory).get(TvShowViewModel::class.java)
  }

  fun provideTvShowViewModel(baseRoomDatabase: BaseRoomDatabase, fragment: Fragment): TvShowViewModel {
    val viewModelFactory = provideTvShowViewModelFactory(baseRoomDatabase)
    return ViewModelProviders.of(fragment, viewModelFactory).get(TvShowViewModel::class.java)
  }

  private fun provideTvShowViewModelFactory(baseRoomDatabase: BaseRoomDatabase): TvShowViewModelFactory {
    return TvShowViewModelFactory.getInstance(baseRoomDatabase)
  }

}