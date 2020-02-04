package com.kursigoyang.android_digitalent.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kursigoyang.android_digitalent.listener.ResultCallback
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.data.MovieRepository
import com.kursigoyang.android_digitalent.ui.movie.data.source.remote.MovieRemoteRepository
import com.kursigoyang.android_digitalent.utils.DataDummy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

  // To test LiveData it's recommended you do two things:
  //
  // 1. Use InstantTaskExecutorRule
  // 2. Ensure LiveData observation
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var movieRepository: MovieRepository

  @Mock
  private lateinit var movieRemoteRepository: MovieRemoteRepository

  @Before
  fun setUp() {
    //  using spy for mocking class with constructor parameter
    movieRepository = spy(MovieRepository(movieRemoteRepository))
  }

  @Test
  fun successCallbackRemoteRepository() {

    val dummy = DataDummy.getMovies()

    //   doAnswer used if there are callback or void method
    doAnswer { invocation ->
      (invocation.arguments[0] as ResultCallback<MutableList<Movie>>).onSuccess(dummy)
      null
    }.`when`(movieRemoteRepository).getMovies(any())
    movieRepository.getMovies(object : ResultCallback<MutableList<Movie>> {
      override fun onSuccess(data: MutableList<Movie>?) {
        Assert.assertEquals(3, data?.size)
      }

      override fun onFailure(message: String, e: Exception) {
      }
    })
    verify(movieRepository).getMovies(any())


  }
}