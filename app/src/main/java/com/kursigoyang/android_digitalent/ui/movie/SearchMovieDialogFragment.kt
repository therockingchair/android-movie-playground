package com.kursigoyang.android_digitalent.ui.movie


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
import com.kursigoyang.android_digitalent.di.Injection
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search_movie_dialog.*


class SearchMovieDialogFragment : DialogFragment() {

  private lateinit var movieAdapter: MovieAdapter
  private lateinit var movieViewModel: MovieViewModel

  companion object {
    val instance by lazy { SearchMovieDialogFragment() }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    movieViewModel = Injection.provideMovieViewModel(context!!, this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_search_movie_dialog, container, false)
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
        movieViewModel.searchMovies(newText!!)
        return true
      }
    })

    movieViewModel.movies.observe(viewLifecycleOwner, Observer {
      addAllItem(it)
    })

    setupAdapter()
    setupRecycle()

  }

  private fun setupAdapter() {
    movieAdapter = MovieAdapter(context!!)
  }

  private fun setupRecycle() {

    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    rvContent.run {
      setHasFixedSize(true)
      this.layoutManager = layoutManager
      adapter = movieAdapter
    }
  }

  private fun addAllItem(movies: MutableList<Movie>) {
    movieAdapter.clearAndaddAll(movies)
  }

  override fun show(manager: FragmentManager, tag: String?) {
    if (!instance.isAdded) {
      super.show(manager, tag)
    }
  }
}
