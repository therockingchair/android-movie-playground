package com.kursigoyang.android_digitalent.utils.network

import com.kursigoyang.android_digitalent.model.BaseResponse
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.model.TvShow
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BaseApiService {

  @GET("discover/movie")
  fun getMovies(): Deferred<Response<BaseResponse<MutableList<Movie>>>>

  @GET("search/movie")
  fun searchMovies(@Query("query") query: String): Deferred<Response<BaseResponse<MutableList<Movie>>>>

  @GET("discover/tv")
  fun getTvShows(): Deferred<Response<BaseResponse<MutableList<TvShow>>>>

  @GET("search/tv")
  fun searchTvShow(@Query("query") query: String): Deferred<Response<BaseResponse<MutableList<TvShow>>>>

  @GET("discover/movie")
  fun getReleaseMovies(@Query("primary_release_date.gte") dateGte: String,@Query("primary_release_date.lte") dateLte: String): Deferred<Response<BaseResponse<MutableList<Movie>>>>

}