package org.twodee.lonelyphone

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Switch
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : Activity() {
  private lateinit var cryWhenLonelySwitch: Switch

  // Exercise 1
  private val serviceIntent
    get() = Intent(this, CryService::class.java)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    cryWhenLonelySwitch = findViewById(R.id.cryWhenLonelySwitch)

    createNotificationChannel()

    // Exercise 11
    LocalBroadcastManager.getInstance(this).let { broadcastManager ->
      val pongReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
          cryWhenLonelySwitch.isChecked = true
        }
      }
      broadcastManager.registerReceiver(pongReceiver, IntentFilter("pong"))
      broadcastManager.sendBroadcastSync(Intent("ping"))
    }

    // Exercise 2
    cryWhenLonelySwitch.setOnCheckedChangeListener { _, isOn ->
      if (isOn) {
        startForegroundService(serviceIntent)
      } else {
        stopService(serviceIntent)
      }
    }
  }

  // Exercise 3
  private fun createNotificationChannel() {
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(Notification.CATEGORY_ALARM, "Lonely Phone Notifications", importance).apply {
      description = "Hear the plaintive cry of your phone"
    }
    val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
  }
}
