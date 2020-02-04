package com.kursigoyang.android_digitalent.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kursigoyang.android_digitalent.model.TvShow

@Dao
interface FavoriteTvShowDao {

  @Query("SELECT * from tvshow_table ORDER BY id ASC")
  fun getData(): LiveData<MutableList<TvShow>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(tvShow: TvShow)

  @Query("DELETE FROM tvshow_table WHERE id = :id")
  suspend fun deleteById(id: Int)
}