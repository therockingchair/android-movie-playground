package com.kursigoyang.android_digitalent.listener

interface ResultCallback<T> {

  fun onSuccess(data: T?)

  fun onFailure(message: String, e: Exception)
}