package com.marshallaf.mobileui.test

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import com.marshallaf.mobileui.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApplication : Application(), HasActivityInjector {

  @Inject lateinit var injector: DispatchingAndroidInjector<Activity>
  private lateinit var appComponent: TestApplicationComponent

  companion object {
    fun appComponent(): TestApplicationComponent {
      return (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication).appComponent
    }
  }

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerTestApplicationComponent.builder().application(this).build
    appComponent.inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = injector
}
