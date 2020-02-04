package com.kursigoyang.android_digitalent.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kursigoyang.android_digitalent.model.Movie

@Dao
interface MovieDao {


  @Query("SELECT * from movie_table ORDER BY id ASC")
  fun getData(): LiveData<MutableList<Movie>>

  @Query("SELECT * from movie_table ORDER BY id ASC")
  fun getDataPagination(): DataSource.Factory<Int, Movie>

  @Query("SELECT * from movie_table WHERE id = :id")
  suspend fun isFavorite(id: Int): Movie

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(movie: Movie)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(movies: MutableList<Movie>)

  @Query("DELETE FROM movie_table WHERE id = :id")
  suspend fun deleteById(id: Int)

  @Query("SELECT * from movie_table ORDER BY id ASC")
  fun getDataCursor(): Cursor
}