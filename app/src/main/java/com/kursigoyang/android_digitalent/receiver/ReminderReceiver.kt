package com.kursigoyang.android_digitalent.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.kursigoyang.android_digitalent.R
import com.kursigoyang.android_digitalent.model.Movie
import com.kursigoyang.android_digitalent.ui.movie.MovieDetailActivity
import com.kursigoyang.android_digitalent.utils.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ReminderReceiver : BroadcastReceiver() {

  companion object {
    const val RELEASE_ID = 100
    const val DAILY_ID = 101

    const val MESSAGE = "message"
    const val TITLE = "title"
    const val TYPE = "type"

  }

  override fun onReceive(context: Context, intent: Intent) {
    val type: Int = intent.getIntExtra(TYPE, 0)
    val title: String = intent.getStringExtra(TITLE)
    val message: String = intent.getStringExtra(MESSAGE)

    if (type == RELEASE_ID) {
      getReleaseMovie(context)
    } else {
      showNotification(context, title, message, type)
    }
  }

  private fun getReleaseMovie(ctx: Context) {

    CoroutineScope(Dispatchers.IO).launch {
      try {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val dateString = format.format(Calendar.getInstance().time)

        val response = ApiService.instance.getReleaseMovies(dateString, dateString).await()

        if (response.isSuccessful) {
          for (movie in response.body()?.result!!) {
            showNotification(ctx, movie.title!!, ctx.getString(R.string.movie_has_release, movie.title), movie.id, movie)
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

  }


  fun setRelease(ctx: Context) {
    val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(ctx, ReminderReceiver::class.java).apply {
      putExtra(TITLE, ctx.getString(R.string.app_name))
      putExtra(MESSAGE, ctx.getString(R.string.daily_reminder_notif_message))
      putExtra(TYPE, RELEASE_ID)
    }

    val calendar = Calendar.getInstance().apply {
      timeInMillis = System.currentTimeMillis()
      set(Calendar.HOUR_OF_DAY, 8)
    }

    val pendingIntent = PendingIntent.getBroadcast(ctx, RELEASE_ID, intent, 0)
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

  }

  fun setDaily(ctx: Context) {
    val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(ctx, ReminderReceiver::class.java).apply {
      putExtra(TITLE, ctx.getString(R.string.app_name))
      putExtra(MESSAGE, ctx.getString(R.string.daily_reminder_notif_message))
      putExtra(TYPE, DAILY_ID)
    }

    val calendar = Calendar.getInstance().apply {
      timeInMillis = System.currentTimeMillis()
      set(Calendar.HOUR_OF_DAY, 7)
    }

    val pendingIntent = PendingIntent.getBroadcast(ctx, DAILY_ID, intent, 0)
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
  }


  fun cancelReminder(ctx: Context, type: Int) {
    val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(ctx, ReminderReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(ctx, type, intent, 0)
    pendingIntent.cancel()
    alarmManager.cancel(pendingIntent)
  }

  fun hasReminder(ctx: Context, type: Int): Boolean {
    val intent = Intent(ctx, ReminderReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(ctx, type, intent, PendingIntent.FLAG_NO_CREATE)
    return pendingIntent != null
  }

  private fun showNotification(ctx: Context, title: String, message: String, notifId: Int, movie: Movie? = null) {
    val CHANNEL_ID = "channel_id"
    val CHANNEL_NAME = "AlarmManager"

    val notificationManagerCompat = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
      .setSmallIcon(android.R.drawable.ic_lock_idle_alarm).setContentTitle(title)
      .setContentText(message).setColor(ContextCompat.getColor(ctx, android.R.color.transparent))
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000)).setSound(alarmSound)
      .setAutoCancel(true)

    if (movie != null) {
      val moviePendingIntent = PendingIntent.getActivity(ctx, 0, MovieDetailActivity.createIntent(ctx, movie), PendingIntent.FLAG_UPDATE_CURRENT)
      builder.setContentIntent(moviePendingIntent)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

      val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

      channel.enableVibration(true)
      channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

      builder.setChannelId(CHANNEL_ID)

      notificationManagerCompat.createNotificationChannel(channel)
    }

    val notification = builder.build()

    notificationManagerCompat.notify(notifId, notification)

  }
}