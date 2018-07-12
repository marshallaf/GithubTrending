package com.marshallaf.githubtrending

import android.app.Application
import android.os.Environment
import java.io.File

class App : Application() {

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      val sdcard = Environment.getExternalStorageDirectory()
      val coverageDir = File(sdcard, "coverage")
      coverageDir.mkdirs()
    }
  }
}