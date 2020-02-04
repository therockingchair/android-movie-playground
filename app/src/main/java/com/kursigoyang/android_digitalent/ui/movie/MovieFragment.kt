package com.kursigoyang.android_digitalent.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.di.Injection
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.viewmodel.MovieViewModel
import com.kursigoyang.android_digitalent.utils.ErrorHelper
import com.kursigoyang.android_digitalent.utils.toast
import kotlinx.android.synthetic.main.fragment_movie.*


/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

  private lateinit var movieAdapter: MovieAdapter
  private lateinit var movieViewModel: MovieViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    movieViewModel = Injection.provideMovieViewModel(context!!, this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_movie, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupAdapter()
    setupRecycle()

    movieViewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
      addAllItem(movies)
    })
    movieViewModel.error.observe(viewLifecycleOwner, Observer { error ->
      context?.toast(ErrorHelper.message(context!!, error))
      progressBar?.visibility = View.GONE
    })

    movieViewModel.loadMovies()
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
    progressBar?.visibility = View.GONE
  }
}
