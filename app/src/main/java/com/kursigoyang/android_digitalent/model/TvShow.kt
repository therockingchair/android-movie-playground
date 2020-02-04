package com.kursigoyang.android_digitalent.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvshow_table")
class TvShow() : Parcelable {

  @SerializedName("id")
  @PrimaryKey
  var id: Int = 0
  @SerializedName("original_name")
  var title: String? = ""
  @SerializedName("overview")
  var overview: String? = ""
  @SerializedName("first_air_date")
  var releaseDate: String? = ""
  @SerializedName("poster_path")
  var posterPath: String? = ""
  @SerializedName("backdrop_path")
  var backdropPath: String? = ""
  @SerializedName("vote_average")
  var voteAverage: Float = 0f
  @SerializedName("vote_count")
  var voteCount: Int = 0

  var contentType: String = "TvShow"

  constructor(parcel: Parcel) : this() {
    id = parcel.readInt()
    title = parcel.readString()
    overview = parcel.readString()
    releaseDate = parcel.readString()
    posterPath = parcel.readString()
    backdropPath = parcel.readString()
    voteAverage = parcel.readFloat()
    voteCount = parcel.readInt()
    contentType = parcel.readString()
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(id)
    parcel.writeString(title)
    parcel.writeString(overview)
    parcel.writeString(releaseDate)
    parcel.writeString(posterPath)
    parcel.writeString(backdropPath)
    parcel.writeFloat(voteAverage)
    parcel.writeInt(voteCount)
    parcel.writeString(contentType)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<TvShow> {
    override fun createFromParcel(parcel: Parcel): TvShow {
      return TvShow(parcel)
    }

    override fun newArray(size: Int): Array<TvShow?> {
      return arrayOfNulls(size)
    }
  }


}