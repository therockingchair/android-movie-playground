package com.kursigoyang.android_digitalent.utils

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.provider.DatabaseContract
import com.kursigoyang.android_digitalent.ui.widget.FavoriteMovieWidget
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


internal class StackRemoteViewsFactory(private val application: Application) : RemoteViewsService.RemoteViewsFactory {
  private val mWidgetItems = mutableListOf<Movie>()
  private val mBitmap = mutableListOf<Bitmap>()

  override fun onCreate() {}

  override fun onDestroy() {
  }

  override fun getLoadingView(): RemoteViews? = null

  override fun getItemId(position: Int): Long = 0

  override fun onDataSetChanged() {
    val identityToken = Binder.clearCallingIdentity()
    val cursor = application.contentResolver.query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null, null)

    mWidgetItems.clear()
    mBitmap.clear()

      while (cursor?.moveToNext()!!) {
        val posterPath = cursor.getString(cursor.getColumnIndexOrThrow(Movie.POSTER_PATH))
        mWidgetItems.add(MapHelper.cursorToMovie(cursor))

        runBlocking {
          try {
            val url = URL("https://image.tmdb.org/t/p/w500$posterPath")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            mBitmap.add(BitmapFactory.decodeStream(input))
          } catch (e: IOException) {
            Log.d("cursor", "error")
          }
        }
      }
    cursor.close()
    Binder.restoreCallingIdentity(identityToken)
  }

  override fun hasStableIds(): Boolean = false

  override fun getViewAt(position: Int): RemoteViews {
    val rv = RemoteViews(application.packageName, R.layout.item_widget_favorite_movie)

    if (mWidgetItems.size > 0) {
      rv.setTextViewText(R.id.txtTitle, mWidgetItems[position].title)
      rv.setImageViewBitmap(R.id.imgContent, mBitmap[position])

      val extras = Bundle().apply {
        putParcelable(FavoriteMovieWidget.MOVIE, mWidgetItems[position])
      }

      val fillInIntent = Intent()
      fillInIntent.putExtras(extras)
      rv.setOnClickFillInIntent(R.id.imgContent, fillInIntent)
    }

    return rv
  }

  override fun getCount(): Int = mWidgetItems.size

  override fun getViewTypeCount(): Int = 1

}