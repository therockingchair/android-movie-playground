package com.kursigoyang.android_digitalent.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kursigoyang.android_digitalent.model.BaseResponse
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.model.TvShow
import java.io.IOException


object DataDummy {

  fun getMovies(): MutableList<Movie>? {

    val dummyData = "{\"results\":[{\"popularity\":743.027,\"vote_count\":1927,\"video\":false,\"poster_path\":\"/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg\",\"id\":419704,\"adult\":false,\"backdrop_path\":\"/5BwqwxMEjeFtdknRV792Svo0K1v.jpg\",\"original_language\":\"en\",\"original_title\":\"Ad Astra\",\"genre_ids\":[12,18,9648,878,53],\"title\":\"Ad Astra\",\"vote_average\":6,\"overview\":\"The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.\",\"release_date\":\"2019-09-17\"},{\"popularity\":408.15,\"vote_count\":130,\"video\":false,\"poster_path\":\"/y95lQLnuNKdPAzw9F9Ab8kJ80c3.jpg\",\"id\":38700,\"adult\":false,\"backdrop_path\":\"/upUy2QhMZEmtypPW3PdieKLAHxh.jpg\",\"original_language\":\"en\",\"original_title\":\"Bad Boys for Life\",\"genre_ids\":[28,80,53],\"title\":\"Bad Boys for Life\",\"vote_average\":6.6,\"overview\":\"Marcus and Mike are forced to confront new threats, career changes, and midlife crises as they join the newly created elite team AMMO of the Miami police department to take down the ruthless Armando Armas, the vicious leader of a Miami drug cartel.\",\"release_date\":\"2020-01-15\"},{\"popularity\":303.679,\"vote_count\":1021,\"video\":false,\"poster_path\":\"/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg\",\"id\":290859,\"adult\":false,\"backdrop_path\":\"/riTANvQ8GKmQbgtC1ps3OfkU43A.jpg\",\"original_language\":\"en\",\"original_title\":\"Terminator: Dark Fate\",\"genre_ids\":[28,878],\"title\":\"Terminator: Dark Fate\",\"vote_average\":6.2,\"overview\":\"Decades after Sarah Connor prevented Judgment Day, a lethal new Terminator is sent to eliminate the future leader of the resistance. In a fight to save mankind, battle-hardened Sarah Connor teams up with an unexpected ally and an enhanced super soldier to stop the deadliest Terminator yet.\",\"release_date\":\"2019-10-23\"}]}"
    val movies = mutableListOf<Movie>()
    try {
      val gson = GsonBuilder()
      val collectionType = object : TypeToken<BaseResponse<MutableList<Movie>>>() {}.type

      val response: BaseResponse<MutableList<Movie>> = gson.create()
        .fromJson(dummyData, collectionType)

      for (movie in response.result!!) {
        movies.add(movie)
      }
    } catch (ex: IOException) {
      ex.printStackTrace()
      return null
    }

    return movies
  }

  fun getTvShow(): MutableList<TvShow>? {

    val dummyData = "{\"results\":[{\"original_name\":\"Arrow\",\"genre_ids\":[80,18,9648,10759],\"name\":\"Arrow\",\"popularity\":361.404,\"origin_country\":[\"US\"],\"vote_count\":3028,\"first_air_date\":\"2012-10-10\",\"backdrop_path\":\"/dKxkwAJfGuznW8Hu0mhaDJtna0n.jpg\",\"original_language\":\"en\",\"id\":1412,\"vote_average\":5.9,\"overview\":\"Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.\",\"poster_path\":\"/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg\"},{\"original_name\":\"The Simpsons\",\"genre_ids\":[16,35],\"name\":\"The Simpsons\",\"popularity\":239.697,\"origin_country\":[\"US\"],\"vote_count\":2343,\"first_air_date\":\"1989-12-17\",\"backdrop_path\":\"/f5uNbUC76oowt5mt5J9QlqrIYQ6.jpg\",\"original_language\":\"en\",\"id\":456,\"vote_average\":7.2,\"overview\":\"Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.\",\"poster_path\":\"/qcr9bBY6MVeLzriKCmJOv1562uY.jpg\"},{\"original_name\":\"Rick and Morty\",\"genre_ids\":[16,35,10765],\"name\":\"Rick and Morty\",\"popularity\":189.678,\"origin_country\":[\"US\"],\"vote_count\":1668,\"first_air_date\":\"2013-12-02\",\"backdrop_path\":\"/mzzHr6g1yvZ05Mc7hNj3tUdy2bM.jpg\",\"original_language\":\"en\",\"id\":60625,\"vote_average\":8.6,\"overview\":\"Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.\",\"poster_path\":\"/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg\"}]}"
    val data = mutableListOf<TvShow>()
    try {
      val gson = GsonBuilder()
      val collectionType = object : TypeToken<BaseResponse<MutableList<TvShow>>>() {}.type

      val response: BaseResponse<MutableList<TvShow>> = gson.create()
        .fromJson(dummyData, collectionType)

      for (tv in response.result!!) {
        data.add(tv)
      }
    } catch (ex: IOException) {
      ex.printStackTrace()
      return null
    }

    return data
  }
}