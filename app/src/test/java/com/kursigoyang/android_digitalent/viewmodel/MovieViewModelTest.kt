package com.kursigoyang.android_digitalent.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.data.MovieRepository
import com.kursigoyang.android_digitalent.ui.movie.viewmodel.MovieViewModel
import com.kursigoyang.android_digitalent.utils.DataDummy
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

  // To test LiveData it's recommended you do two things:
  //
  // 1. Use InstantTaskExecutorRule
  // 2. Ensure LiveData observation
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Mock
  private lateinit var movieRepository: MovieRepository

  lateinit var viewModel: MovieViewModel

  @Mock
  private lateinit var observer: Observer<MutableList<Movie>>

  @Before
  fun setUp() {
    //  using spy for mocking class with constructor parameter
    viewModel = spy(MovieViewModel(movieRepository))
  }

  @Test
  fun loadMovies() {

    val dummySource = DataDummy.getMovies()
    val dummy = MutableLiveData<MutableList<Movie>>()
    dummy.value = dummySource

    // when = memilih event mana yang akan dimanipulasi behavior nya
    // thenReturn = memanipulasi output
    `when`(viewModel.movies).thenReturn(dummy)
    Assert.assertNotNull(viewModel.movies)
    Assert.assertEquals(3, viewModel.movies.value?.size)

    viewModel.movies.observeForever(observer)
    verify(observer).onChanged(dummySource)
  }
}