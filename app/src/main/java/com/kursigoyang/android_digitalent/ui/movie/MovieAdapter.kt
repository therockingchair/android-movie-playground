package com.kursigoyang.android_digitalent.ui.movie

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.common.BaseRecycleViewHolder
import com.kursigoyang.android_digitalent.model.Movie
import kotlinx.android.synthetic.main.item_movie.*

class MovieAdapter(var ctx: Context) : RecyclerView.Adapter<MovieAdapter.ContentViewHolder>() {

  private var listItem: MutableList<Movie> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
    return ContentViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_movie, parent, false))
  }

  override fun getItemCount(): Int = listItem.size

  override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  fun clearAndaddAll(data: MutableList<Movie>) {
    listItem.clear()
    listItem.addAll(data)
    notifyDataSetChanged()
  }

  fun getItem(position: Int): Movie {
    return listItem[position]
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