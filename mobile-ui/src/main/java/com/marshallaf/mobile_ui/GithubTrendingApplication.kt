package com.marshallaf.mobile_ui

import android.app.Application
import timber.log.Timber

class GithubTrendingApplication: Application() {

  override fun onCreate() {
    super.onCreate()
    setUpTimber()
  }

  private fun setUpTimber() {
    Timber.plant(Timber.DebugTree())
  }
}