package com.kursigoyang.android_digitalent.utils

import android.content.Intent
import android.widget.RemoteViewsService
import com.kursigoyang.android_digitalent.model.Movie

class StackWidgetService : RemoteViewsService() {
  override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = StackRemoteViewsFactory(this.application)
}