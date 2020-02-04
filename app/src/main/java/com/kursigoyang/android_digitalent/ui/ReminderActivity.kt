package com.kursigoyang.android_digitalent.ui.widget

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.receiver.ReminderReceiver
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {

  private lateinit var reminderReceiver: ReminderReceiver

  companion object {

    fun start(ctx: Context) {
      val intent = Intent(ctx, ReminderActivity::class.java)
      ctx.startActivity(intent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_reminder)

    toolbar.setOnClickListener { finish() }

    reminderReceiver = ReminderReceiver()

    swDaily.isChecked = reminderReceiver.hasReminder(this, ReminderReceiver.DAILY_ID)
    swRelease.isChecked = reminderReceiver.hasReminder(this, ReminderReceiver.RELEASE_ID)

    swDaily.setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        reminderReceiver.setDaily(this)
      } else {
        reminderReceiver.cancelReminder(this, ReminderReceiver.DAILY_ID)
      }
    }

    swRelease.setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked) {
        reminderReceiver.setRelease(this)
      } else {
        reminderReceiver.cancelReminder(this, ReminderReceiver.RELEASE_ID)
      }
    }
  }
}