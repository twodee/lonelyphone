package org.twodee.lonelyphone

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.math.absoluteValue

class CryService : Service() {
  private var player: MediaPlayer? = null
  private lateinit var sensorManager: SensorManager

  // Exercise 7
  private var isFlat = false
  private val gravityListener = object : SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
      val isFlatNow = event.values[2].absoluteValue > 9.5
      if (isFlat != isFlatNow) {
        isFlat = isFlatNow
        if (isFlat) {
          startRinging()
        } else {
          stopRinging()
        }
      }
    }
  }

  // Exercise 12
  override fun onCreate() {
    super.onCreate()
    LocalBroadcastManager.getInstance(this).let { broadcastManager ->
      val pingReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
          broadcastManager.sendBroadcastSync(Intent("pong"))
        }
      }
      broadcastManager.registerReceiver(pingReceiver, IntentFilter("ping"))
    }
  }

  // Exercise 4
  override fun onBind(intent: Intent?) = null

  // Exercise 9
  override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
    becomeForegroundService()

    sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY).let { sensor ->
      sensorManager.registerListener(gravityListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    return START_STICKY
  }

  // Exercise 10
  override fun onDestroy() {
    super.onDestroy()
    sensorManager.unregisterListener(gravityListener)
    stopRinging()
  }

  // Exercise 8
  private fun becomeForegroundService() {
    val intent = Intent(this, MainActivity::class.java).let {
      PendingIntent.getActivity(this, 0, it, 0)
    }

    val notification = Notification.Builder(this, Notification.CATEGORY_ALARM).run {
      setSmallIcon(R.drawable.baby)
      setContentTitle("Lonely Phone")
      setContentText("Don't let me down.")
      setContentIntent(intent)
      setAutoCancel(true)
      build()
    }

    startForeground(1, notification)
  }

  // Exercise 5
  private fun startRinging() {
    val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
    player = MediaPlayer.create(this, ringtoneUri).apply {
      isLooping = true
      start()
    }
  }

  // Exercise 6
  private fun stopRinging() {
    player?.apply {
      stop()
      release()
    }
    player = null
  }
}
