package com.kursigoyang.android_digitalent.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

  private val RESOURCE = "GLOBAL"
  val countingIdlingResource = CountingIdlingResource(RESOURCE)

  fun increment() {
    countingIdlingResource.increment()
  }

  fun decrement() {
    if (!countingIdlingResource.isIdleNow) {
      //Memberitahukan bahwa tugas sudah selesai dijalankan
      countingIdlingResource.decrement()
    }
  }
}