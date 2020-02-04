package com.kursigoyang.android_digitalent.common

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseRecycleViewHolder(protected var view: View) : RecyclerView.ViewHolder(view), LayoutContainer, LifecycleOwner {

  override val containerView: View?
    get() = view

  private var lifecycleRegistry: LifecycleRegistry? = null

  init {
    if (lifecycleRegistry == null) {
      lifecycleRegistry = LifecycleRegistry(this)
    }
    lifecycleRegistry?.markState(Lifecycle.State.INITIALIZED)
  }

  fun onAttach() {
    lifecycleRegistry?.markState(Lifecycle.State.STARTED)
  }

  fun onRecycled() {
    lifecycleRegistry?.markState(Lifecycle.State.DESTROYED)
  }

  override fun getLifecycle(): Lifecycle {
    return lifecycleRegistry!!
  }
}
