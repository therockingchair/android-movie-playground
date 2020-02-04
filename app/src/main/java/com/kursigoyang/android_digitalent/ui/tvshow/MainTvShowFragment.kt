package com.kursigoyang.android_digitalent.ui.tvshow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.model.ContentPager
import com.kursigoyang.android_digitalent.ui.ContentPagerAdapter
import kotlinx.android.synthetic.main.fragment_main_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MainTvShowFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_main_tv_show, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val contentPagers = mutableListOf<ContentPager>().apply {

      add(ContentPager().apply {
        title = R.string.tv_show
        fragment = TvFragment()
      })

      add(ContentPager().apply {
        title = R.string.favorite_tvshow
        fragment = FavoriteTvShowFragment()
      })

    }

    val pagerAdapter = ContentPagerAdapter(context!!, childFragmentManager, contentPagers)
    pager.adapter = pagerAdapter
    lyTab.setupWithViewPager(pager)
  }

}
