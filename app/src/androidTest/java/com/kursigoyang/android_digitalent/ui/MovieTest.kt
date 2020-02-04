package com.kursigoyang.android_digitalent.ui

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.ui.movie.MovieFragment
import com.kursigoyang.android_digitalent.ui.movie.SearchMovieDialogFragment
import com.kursigoyang.android_digitalent.util.typeSearchViewText
import com.kursigoyang.android_digitalent.utils.EspressoIdlingResource
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieTest {

  @get:Rule
  var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

  @Before
  fun setUp() {
    IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
  }

  @After
  fun tearDown() {
    IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
  }

  @Test
  fun loadMovie() {
    val scenario = launchFragmentInContainer<MovieFragment>()
    scenario.recreate()
    scenario.onFragment {
      Assert.assertTrue(it.view?.findViewById<RecyclerView>(R.id.rvContent)?.adapter!!.itemCount > 0)
    }
  }

  @Test
  fun searchMovie() {
    with(launchFragment<SearchMovieDialogFragment>()) {
      onView(withId(R.id.inpSearch)).perform(typeSearchViewText("joker"))
      onView(withId(R.id.rvContent)).check { view, _ ->
        val rv = view as RecyclerView
        Assert.assertTrue(rv.adapter!!.itemCount > 0)
      }
    }
  }
}