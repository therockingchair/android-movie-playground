package com.kursigoyang.android_digitalent.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class BaseResponse<T>(parcel: Parcel) : Parcelable {

  @SerializedName("results")
   var result: T? = null


  override fun writeToParcel(parcel: Parcel, flags: Int) {

  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<BaseResponse<*>> {
    override fun createFromParcel(parcel: Parcel): BaseResponse<*> {
      return BaseResponse<Any>(parcel)
    }

    override fun newArray(size: Int): Array<BaseResponse<*>?> {
      return arrayOfNulls(size)
    }
  }
}