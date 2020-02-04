package com.kursigoyang.android_digitalent.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kursigoyang.android_digitalent.R
import kotlinx.android.synthetic.main.activity_movie_detail.*

class AboutActivity : AppCompatActivity() {

  companion object {

    fun start(ctx: Context) {
      val intent = Intent(ctx, AboutActivity::class.java)
      ctx.startActivity(intent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_about)

    toolbar.setOnClickListener {
      finish()
    }
  }

}
