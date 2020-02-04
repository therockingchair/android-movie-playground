package com.kursigoyang.android_digitalent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kursigoyang.android_digitalent.database.dao.FavoriteTvShowDao
import com.kursigoyang.android_digitalent.database.dao.MovieDao
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.model.TvShow

@Database(entities = [Movie::class, TvShow::class], version = 6, exportSchema = false)
abstract class BaseRoomDatabase : RoomDatabase() {
  abstract fun movieDao(): MovieDao
  abstract fun favoriteTvShowDao(): FavoriteTvShowDao

  companion object {
    // Singleton prevents multiple instances of database opening at the
    // same time.
    @Volatile
    private var INSTANCE: BaseRoomDatabase? = null

    fun getDatabase(context: Context?): BaseRoomDatabase {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.databaseBuilder(context?.applicationContext!!, BaseRoomDatabase::class.java, "base_database")
          .fallbackToDestructiveMigration().build()
        INSTANCE = instance
        return instance
      }
    }
  }
}