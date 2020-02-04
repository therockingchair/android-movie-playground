package com.kursigoyang.android_digitalent.ui.movie

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.di.Injection
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.viewmodel.MovieViewModel
import com.kursigoyang.android_digitalent.ui.widget.FavoriteMovieWidget
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
  private lateinit var movieDetailViewModel: MovieViewModel

  private lateinit var content: Movie
  private var isFavorite: Boolean = false

  companion object {

    fun start(ctx: Context, movie: Movie?) {
      ctx.startActivity(createIntent(ctx, movie))
    }

    fun startNewTask(ctx: Context, movie: Movie?) {
      val intent = createIntent(ctx, movie).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
      }
      ctx.startActivity(intent)
    }

    fun createIntent(ctx: Context, movie: Movie?): Intent = Intent(ctx, MovieDetailActivity::class.java).apply {
      putExtra("movie", movie)
    }

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_detail)

    toolbar.setOnClickListener {
      onBackPressed()
    }
    movieDetailViewModel = Injection.provideMovieViewModel(this)

    content = intent.getParcelableExtra("movie")

    if (movieDetailViewModel.isFavorite(content.id) != null) {
      updateFavorite(true)
    }

    loadData()
  }


  override fun onBackPressed() {

    if (isTaskRoot) {
      val intent = NavUtils.getParentActivityIntent(this)
      startActivity(intent)
      finish()
    } else {
      super.onBackPressed()
    }
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
    movieDetailViewModel.insertFavorite(content)
    updateFavorite(true)
    FavoriteMovieWidget.sendBroadcastUpdateWidget(applicationContext)
  }

  private fun deleteFavorite() {
    movieDetailViewModel.deleteFavorite(content)
    updateFavorite(false)
    FavoriteMovieWidget.sendBroadcastUpdateWidget(applicationContext)

  }

}
