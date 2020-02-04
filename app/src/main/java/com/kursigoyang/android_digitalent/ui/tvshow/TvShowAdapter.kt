package com.kursigoyang.android_digitalent.ui.tvshow

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.common.BaseRecycleViewHolder
import com.kursigoyang.android_digitalent.model.TvShow
import kotlinx.android.synthetic.main.item_tvshow.*

class TvShowAdapter(var ctx: Context) : RecyclerView.Adapter<TvShowAdapter.ContentViewHolder>() {

  private var listItem: MutableList<TvShow> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
    return ContentViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_tvshow, parent, false))
  }

  override fun getItemCount(): Int = listItem.size

  override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  fun clearAndaddAll(data: MutableList<TvShow>) {
    listItem.clear()
    listItem.addAll(data)
    notifyDataSetChanged()
  }

  fun getItem(position: Int): TvShow {
    return listItem[position]
  }

  inner class ContentViewHolder(itemView: View) : BaseRecycleViewHolder(itemView) {

    fun bind(tv: TvShow) {

      txtName.text = tv.title
      txtVersion.text = tv.overview

      imgIcon.setImageURI(Uri.parse("https://image.tmdb.org/t/p/w500" + tv.posterPath))

      itemView.setOnClickListener {
        TvShowDetailActivity.start(ctx, tv)
      }
    }

  }
}