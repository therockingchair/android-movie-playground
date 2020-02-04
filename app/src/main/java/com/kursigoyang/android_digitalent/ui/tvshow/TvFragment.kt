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
import com.kursigoyang.android_digitalent.model.TvShow
import com.kursigoyang.android_digitalent.ui.tvshow.viewmodel.TvShowViewModel
import com.kursigoyang.android_digitalent.utils.ErrorHelper
import com.kursigoyang.android_digitalent.utils.toast
import kotlinx.android.synthetic.main.fragment_tv.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TvFragment : Fragment() {

  private lateinit var tvShowAdapter: TvShowAdapter
  private lateinit var tvShowViewModel: TvShowViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    tvShowViewModel = Injection.provideTvShowViewModel(BaseRoomDatabase.getDatabase(context), this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_tv, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupAdapter()
    setupRecycle()

    tvShowViewModel.tvshows.observe(viewLifecycleOwner, Observer { tvshows ->
      addAllItem(tvshows)
    })
    tvShowViewModel.error.observe(viewLifecycleOwner, Observer { error ->
      context?.toast(ErrorHelper.message(context!!, error))
      progressBar?.visibility = View.GONE
    })

    tvShowViewModel.loadTvShow()
  }

  private fun setupAdapter() {
    tvShowAdapter = TvShowAdapter(context!!)
  }

  private fun setupRecycle() {
    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    rvContent.run {
      setHasFixedSize(true)
      this.layoutManager = layoutManager
      adapter = tvShowAdapter
    }
  }

  private fun addAllItem(tvshows: MutableList<TvShow>) {
    tvShowAdapter.clearAndaddAll(tvshows)
    progressBar?.visibility = View.GONE
  }
}
