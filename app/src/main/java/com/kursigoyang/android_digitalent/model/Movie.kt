package com.kursigoyang.android_digitalent.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie_table")
class Movie() : Parcelable {

  @SerializedName(ID)
  @ColumnInfo(name = ID)
  @PrimaryKey
  var id: Int = 0

  @SerializedName(TITLE)
  @ColumnInfo(name = TITLE)
  var title: String? = ""

  @SerializedName(OVERVIEW)
  @ColumnInfo(name = OVERVIEW)
  var overview: String? = ""

  @SerializedName(TAGLINE)
  @ColumnInfo(name = TAGLINE)
  var tagline: String? = ""

  @SerializedName(RELEASE_DATE)
  @ColumnInfo(name = RELEASE_DATE)
  var releaseDate: String? = ""

  @SerializedName(POSTER_PATH)
  @ColumnInfo(name = POSTER_PATH)
  var posterPath: String? = ""

  @SerializedName(BACKDROP_PATH)
  @ColumnInfo(name = BACKDROP_PATH)
  var backdropPath: String? = ""

  @SerializedName(RUNTIME)
  @ColumnInfo(name = RUNTIME)
  var runtime: String? = ""

  @SerializedName(BUDGET)
  @ColumnInfo(name = BUDGET)
  var budget: String? = ""

  @SerializedName(REVENUE)
  @ColumnInfo(name = REVENUE)
  var revenue: String? = ""

  @SerializedName("features")
  @Ignore
  var features: Array<String> = arrayOf()

  @SerializedName(VOTE_AVERAGE)
  @ColumnInfo(name = VOTE_AVERAGE)
  var voteAverage: Float = 0f

  @SerializedName(VOTE_COUNT)
  @ColumnInfo(name = VOTE_COUNT)
  var voteCount: Int = 0

  var contentType: String = "Movie"

  constructor(parcel: Parcel) : this() {
    id = parcel.readInt()
    title = parcel.readString()
    overview = parcel.readString()
    tagline = parcel.readString()
    releaseDate = parcel.readString()
    posterPath = parcel.readString()
    backdropPath = parcel.readString()
    runtime = parcel.readString()
    budget = parcel.readString()
    revenue = parcel.readString()
    features = parcel.createStringArray()
    voteAverage = parcel.readFloat()
    voteCount = parcel.readInt()
    contentType = parcel.readString()
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(id)
    parcel.writeString(title)
    parcel.writeString(overview)
    parcel.writeString(tagline)
    parcel.writeString(releaseDate)
    parcel.writeString(posterPath)
    parcel.writeString(backdropPath)
    parcel.writeString(runtime)
    parcel.writeString(budget)
    parcel.writeString(revenue)
    parcel.writeStringArray(features)
    parcel.writeFloat(voteAverage)
    parcel.writeInt(voteCount)
    parcel.writeString(contentType)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object {

    const val ID = "id"
    const val TITLE = "title"
    const val OVERVIEW = "overview"
    const val TAGLINE = "tagline"
    const val RELEASE_DATE = "release_date"
    const val POSTER_PATH = "poster_path"
    const val BACKDROP_PATH = "backdrop_path"
    const val RUNTIME = "runtime"
    const val BUDGET = "budget"
    const val REVENUE = "revenue"
    const val VOTE_AVERAGE = "vote_average"
    const val VOTE_COUNT = "vote_count"

    @JvmField
    val CREATOR = object : Parcelable.Creator<Movie> {

      override fun createFromParcel(parcel: Parcel): Movie {
        return Movie(parcel)
      }

      override fun newArray(size: Int): Array<Movie?> {
        return arrayOfNulls(size)
      }
    }
  }
}