package org.twodee.lonelyphone

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private lateinit var cryWhenLonelySwitch: Switch

  // Exercise 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    cryWhenLonelySwitch = findViewById(R.id.cryWhenLonelySwitch)

    createNotificationChannel()

    // Exercise 11

    // Exercise 2
  }

  // Exercise 3
  private fun createNotificationChannel() {
  }
}
