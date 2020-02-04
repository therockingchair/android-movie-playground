package com.kursigoyang.android_digitalent.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.database.dao.MovieDao

class FavoriteMovieProvider : ContentProvider() {

  companion object {

    private const val MOVIE = 1
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private lateinit var movieDao: MovieDao

    init {
      uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.MovieColumns.TABLE_NAME, MOVIE)
    }
  }

  override fun onCreate(): Boolean {
    movieDao = BaseRoomDatabase.getDatabase(context).movieDao()
    return true
  }

  override fun insert(uri: Uri, values: ContentValues?): Uri? = null

  override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
    val cursor = when (uriMatcher.match(uri)) {
      MOVIE -> movieDao.getDataCursor()
      else -> null
    }
    cursor?.setNotificationUri(context.contentResolver, uri)
    return cursor
  }

  override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
    return 0
  }

  override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
    return 0
  }

  override fun getType(uri: Uri): String? {
    return null
  }


}