package com.kursigoyang.android_digitalent.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.di.Injection
import com.kursigoyang.android_digitalent.ui.tvshow.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteTvShowFragment : Fragment() {

  private lateinit var favoriteTvShowAdapter: TvShowAdapter
  private lateinit var tvShowViewModel: TvShowViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_favorite, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    tvShowViewModel = Injection.provideTvShowViewModel(BaseRoomDatabase.getDatabase(context), this)
    setupAdapter()
    tvShowViewModel.getFavoritesTvShow()?.observe(viewLifecycleOwner, Observer { tvShow ->
      favoriteTvShowAdapter.clearAndaddAll(tvShow)
      progressBar?.visibility = View.GONE

      if (favoriteTvShowAdapter.itemCount > 0) {
        txtMessage.visibility = View.GONE
      } else {
        txtMessage.visibility = View.VISIBLE
        txtMessage.text = context?.getString(R.string.no_favorite)
      }
    })
  }

  private fun setupAdapter() {
    favoriteTvShowAdapter = TvShowAdapter(context!!)

    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    rvContent.run {
      setHasFixedSize(true)
      this.layoutManager = layoutManager
      adapter = favoriteTvShowAdapter
    }
  }

}
