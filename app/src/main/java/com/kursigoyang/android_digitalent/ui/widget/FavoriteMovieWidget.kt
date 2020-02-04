package com.kursigoyang.android_digitalent.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.ui.movie.MovieDetailActivity
import com.kursigoyang.android_digitalent.utils.StackWidgetService

/**
 * Implementation of App Widget functionality.
 */
class FavoriteMovieWidget : AppWidgetProvider() {

  override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
    for (appWidgetId in appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId)
    }
  }

  override fun onReceive(context: Context, intent: Intent) {
    super.onReceive(context, intent)
    if (intent.action != null) {
      if (intent.action == START_ACT) {
        MovieDetailActivity.startNewTask(context, intent.getParcelableExtra(MOVIE))
      } else if (intent.action == UPDATE_WIDGET) {
        val gm = AppWidgetManager.getInstance(context)
        val ids = gm.getAppWidgetIds(ComponentName(context, FavoriteMovieWidget::class.java))
        gm.notifyAppWidgetViewDataChanged(ids, R.id.stack_view)
      }
    }
  }

  companion object {

    private const val START_ACT = "START_ACT"
    const val MOVIE = "movie"
    private const val UPDATE_WIDGET = "UPDATE_WIDGET"

    internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
      val intent = Intent(context, StackWidgetService::class.java).apply {
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
      }

      val views = RemoteViews(context.packageName, R.layout.favorite_movie_widget).apply {
        setRemoteAdapter(R.id.stack_view, intent)
        setEmptyView(R.id.stack_view, R.id.empty_view)
      }

      val actIntent = Intent(context, FavoriteMovieWidget::class.java).apply {
        action = START_ACT
      }
      val actPendingIntent = PendingIntent.getBroadcast(context, 0, actIntent, PendingIntent.FLAG_UPDATE_CURRENT)
      views.setPendingIntentTemplate(R.id.stack_view, actPendingIntent)

      // Instruct the widget manager to update the widget
      appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    fun sendBroadcastUpdateWidget(ctx: Context) {
      val intent = Intent(ctx, FavoriteMovieWidget::class.java).apply {
        action = UPDATE_WIDGET
      }
      ctx.sendBroadcast(intent)
    }
  }
}

