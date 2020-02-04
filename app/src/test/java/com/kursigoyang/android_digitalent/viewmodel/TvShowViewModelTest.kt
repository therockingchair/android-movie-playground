package com.kursigoyang.android_digitalent.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kursigoyang.android_digitalent.model.TvShow
import com.kursigoyang.android_digitalent.ui.tvshow.viewmodel.TvShowViewModel
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
class TvShowViewModelTest {

  // To test LiveData it's recommended you do two things:
  //
  // 1. Use InstantTaskExecutorRule
  // 2. Ensure LiveData observation
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: TvShowViewModel

  @Mock
  private lateinit var observer: Observer<MutableList<TvShow>>

  @Before
  fun setUp() {
    viewModel = spy(TvShowViewModel())
  }

  @Test
  fun loadTvShow() {

    val dummySource = DataDummy.getTvShow()
    val dummy = MutableLiveData<MutableList<TvShow>>()
    dummy.value = dummySource

    `when`(viewModel.tvshows).thenReturn(dummy)
    Assert.assertNotNull(viewModel.tvshows)
    Assert.assertEquals(3, viewModel.tvshows.value?.size)

    viewModel.tvshows.observeForever(observer)
    verify(observer).onChanged(dummySource)
  }
}