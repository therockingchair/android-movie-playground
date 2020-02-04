package com.kursigoyang.android_digitalent.common

import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco

class BaseApp : MultiDexApplication() {

  override fun onCreate() {
    super.onCreate()
    Fresco.initialize(this)
  }
}