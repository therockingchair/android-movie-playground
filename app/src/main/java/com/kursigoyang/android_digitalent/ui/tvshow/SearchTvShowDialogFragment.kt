package com.kursigoyang.android_digitalent.ui.tvshow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.database.BaseRoomDatabase
import com.kursigoyang.android_digitalent.di.Injection
import com.kursigoyang.android_digitalent.model.TvShow
import com.kursigoyang.android_digitalent.ui.tvshow.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_search_movie_dialog.*


class SearchTvShowDialogFragment : DialogFragment() {


  private lateinit var tvShowAdapter: TvShowAdapter
  private lateinit var tvShowViewModel: TvShowViewModel

  companion object {
    val instance by lazy { SearchTvShowDialogFragment() }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    tvShowViewModel = Injection.provideTvShowViewModel(BaseRoomDatabase.getDatabase(context), this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_search_tv_dialog, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    toolbar.setNavigationOnClickListener { dismiss() }

    inpSearch.onActionViewExpanded()
    inpSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        tvShowViewModel.searchTvShow(newText!!)
        return true
      }
    })

    tvShowViewModel.tvshows.observe(viewLifecycleOwner, Observer {
      addAllItem(it)
    })

    setupAdapter()
    setupRecycle()

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

  private fun addAllItem(tvshow: MutableList<TvShow>) {
    tvShowAdapter.clearAndaddAll(tvshow)
  }

  override fun show(manager: FragmentManager, tag: String?) {
    if (!instance.isAdded) {
      super.show(manager, tag)
    }
  }
}
