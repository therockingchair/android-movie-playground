package com.kursigoyang.android_digitalent.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.common.BaseRecycleViewHolder
import com.kursigoyang.android_digitalent.model.Movie
import kotlinx.android.synthetic.main.item_movie.*

class MoviePagedListAdapter internal constructor(var ctx: Context) : PagedListAdapter<Movie, MoviePagedListAdapter.ContentViewHolder>(DIFF_CALLBACK) {


  companion object {
    private val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
      override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id

      }
      @SuppressLint("DiffUtilEquals")
      override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
    return ContentViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_movie, parent, false))
  }

  override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ContentViewHolder(itemView: View) : BaseRecycleViewHolder(itemView) {

    fun bind(movie: Movie?) {

      txtName.text = movie?.title
      txtVersion.text = movie?.overview

      imgIcon.setImageURI(Uri.parse("https://image.tmdb.org/t/p/w500" + movie?.posterPath))

      itemView.setOnClickListener {
        MovieDetailActivity.start(ctx, movie)
      }
    }

  }
}