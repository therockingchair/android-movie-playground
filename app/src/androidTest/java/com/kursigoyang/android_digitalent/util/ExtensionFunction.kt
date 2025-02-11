package com.kursigoyang.android_digitalent.util

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher


fun typeSearchViewText(text: String): ViewAction {
  return object : ViewAction {
    override fun getDescription(): String = "Change view text"
    override fun getConstraints(): Matcher<View> = allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
    override fun perform(uiController: UiController, view: View) {
      (view as SearchView).setQuery(text, false)
    }
  }
}