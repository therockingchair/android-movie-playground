package com.kursigoyang.android_digitalent.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.di.Injection
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteMovieFragment : Fragment() {

  private lateinit var favoriteMovieAdapter: MoviePagedListAdapter
  private lateinit var movieViewModel: MovieViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_favorite, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    movieViewModel = Injection.provideMovieViewModel(context!!, this)

    setupAdapter()
    movieViewModel.getFavoriteMovies()
      .observe(viewLifecycleOwner, Observer<PagedList<Movie>> { movies ->

        if (movies != null) {
          favoriteMovieAdapter.submitList(movies)
        }
        progressBar?.visibility = View.GONE
      })

    movieViewModel.noFavoriteMovies.observe(viewLifecycleOwner, Observer { show -> showMessageNoContent(show) })
  }

  private fun setupAdapter() {
    favoriteMovieAdapter = MoviePagedListAdapter(context!!)

    val layoutManager  = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    rvContent.run {
      setHasFixedSize(true)
      this.layoutManager = layoutManager
      adapter = favoriteMovieAdapter
    }
  }

  private fun showMessageNoContent(show: Boolean) {
    if (show) {
      txtMessage.visibility = View.VISIBLE
      txtMessage.text = context?.getString(R.string.no_favorite)
    } else {
      txtMessage.visibility = View.GONE
    }

  }

}
