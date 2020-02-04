package com.kursigoyang.android_digitalent.ui

import android.content.Context
import androidx.appcompat.widget.DialogTitle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.model.ContentPager

class ContentPagerAdapter(private val ctx: Context, fm: FragmentManager, private val contentPagers: MutableList<ContentPager>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getItem(position: Int): Fragment = contentPagers[position].fragment

  override fun getCount(): Int = contentPagers.size

  override fun getPageTitle(position: Int): CharSequence? = ctx.getString(contentPagers[position].title)

}