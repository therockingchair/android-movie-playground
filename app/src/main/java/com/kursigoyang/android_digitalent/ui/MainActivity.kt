package com.kursigoyang.android_digitalent.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.ui.widget.ReminderActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.kursigoyang.android_digitalent.ui.movie.MainMovieFragment
import com.kursigoyang.android_digitalent.ui.movie.SearchMovieDialogFragment
import com.kursigoyang.android_digitalent.ui.tvshow.MainTvShowFragment
import com.kursigoyang.android_digitalent.ui.tvshow.SearchTvShowDialogFragment


class MainActivity : AppCompatActivity() {

  private val STATE_RESULT = "state_result"
  private var stateResult: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupContent()

    if (savedInstanceState != null) {
      btmNavigation.selectedItemId = savedInstanceState.getInt(STATE_RESULT)
    } else {
      btmNavigation.selectedItemId = R.id.menuMovie
    }
  }

  override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
    super.onSaveInstanceState(outState, outPersistentState)
    outState?.putInt(STATE_RESULT, stateResult)
  }

  private fun setupContent() {

    setupMenu()

    btmNavigation.setOnNavigationItemSelectedListener {
      val fragment: Fragment
      when (it.itemId) {

        R.id.menuMovie -> {
          fragment = MainMovieFragment()
          openFragment(fragment)

          showMenu(it.itemId)
          return@setOnNavigationItemSelectedListener true
        }
        R.id.menuTvShow -> {
          fragment = MainTvShowFragment()
          openFragment(fragment)

          showMenu(it.itemId)
          return@setOnNavigationItemSelectedListener true

        }
      }
      false
    }
  }

  private fun setupMenu() {
    toolbar.navigationIcon = null
    showMenu()
  }

  private fun showMenu(btmNavItemId: Int = 0) {
    toolbar.menu.clear()
    toolbar.inflateMenu(R.menu.menu_main)
    toolbar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.menuAccount -> {
          AboutActivity.start(this)
        }
        R.id.menuLanguage -> {
          val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
          startActivity(mIntent)
        }
        R.id.menuSearch -> {
          val dialog = if (btmNavigation.selectedItemId == R.id.menuMovie) SearchMovieDialogFragment.instance else SearchTvShowDialogFragment.instance
          dialog.show(supportFragmentManager, "search")
        }
        R.id.menuReminder -> {
          ReminderActivity.start(this)
        }
        else -> {
        }
      }
      return@setOnMenuItemClickListener true
    }

    toolbar.menu.findItem(R.id.menuSearch)
      .setActionView(if (btmNavItemId == R.id.menuMovie) R.layout.item_menu_search_movie else R.layout.item_menu_search_tvshow)

    toolbar.menu.findItem(R.id.menuSearch).actionView.findViewById<LinearLayout>(R.id.lySearchMenu)
      .setOnClickListener {
        val dialog = if (btmNavigation.selectedItemId == R.id.menuMovie) SearchMovieDialogFragment.instance else SearchTvShowDialogFragment.instance
        dialog.show(supportFragmentManager, "search")
      }
  }

  private fun openFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
      .replace(R.id.lyContainer, fragment, fragment.javaClass.simpleName).commit()
  }

}

