package com.kursigoyang.android_digitalent.ui.tvshow

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.di.Injection
import com.kursigoyang.android_digitalent.model.TvShow
import com.kursigoyang.android_digitalent.ui.tvshow.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.activity_tvshow_detail.*

class TvShowDetailActivity : AppCompatActivity() {

  private lateinit var tvShowDetailViewModel: TvShowViewModel
  private lateinit var content: TvShow
  private var isFavorite = false

  companion object {

    fun start(ctx: Context, tvShow: TvShow) {
      val intent = Intent(ctx, TvShowDetailActivity::class.java).apply {
        putExtra("tvshow", tvShow)
      }
      ctx.startActivity(intent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_tvshow_detail)

    toolbar.setOnClickListener {
      finish()
    }
    tvShowDetailViewModel = Injection.provideTvShowViewModel(BaseRoomDatabase.getDatabase(this), this)

    content = intent.getParcelableExtra("tvshow")

    tvShowDetailViewModel.getFavoritesTvShow()?.observe(this, Observer { tvShows ->
      val favorite = tvShows.any { tv -> tv.id == content.id }
      updateFavorite(favorite)
    })

    loadData()
  }

  private fun loadData() {

    txtTitle.text = content.title
    txtOverview.text = content.overview
    imgIcon.setImageURI(Uri.parse("https://image.tmdb.org/t/p/w500" + content.posterPath))
    imgBackdrop.setImageURI(Uri.parse("https://image.tmdb.org/t/p/w500" + content.backdropPath))
    txtDate.text = content.releaseDate
    txtVoteAverage.text = content.voteAverage.toString()
    txtVoteCount.text = content.voteCount.toString()

    imgFavorite.setOnClickListener {
      if (isFavorite) {
        deleteFavorite()
      } else {
        insertFavorite()
      }
    }
  }

  private fun updateFavorite(favorite: Boolean) {
    isFavorite = favorite
    imgFavorite.isSelected = favorite
  }

  private fun insertFavorite() {
    tvShowDetailViewModel.insert(content)
  }

  private fun deleteFavorite() {
    tvShowDetailViewModel.delete(content)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

}
