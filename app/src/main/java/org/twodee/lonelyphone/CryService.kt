package org.twodee.lonelyphone

import android.app.Service
import android.content.Intent
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.IBinder

class CryService : Service() {
  private var player: MediaPlayer? = null
  private lateinit var sensorManager: SensorManager

  // Exercise 7

  // Exercise 12
  override fun onCreate() {
  }

  // Exercise 4
  override fun onBind(p0: Intent?): IBinder? {
    TODO()
  }

  // Exercise 9
  override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
    TODO()
  }

  // Exercise 10
  override fun onDestroy() {
  }

  // Exercise 8
  private fun becomeForegroundService() {
  }

  // Exercise 5
  private fun startRinging() {
  }

  // Exercise 6
  private fun stopRinging() {
  }
}
