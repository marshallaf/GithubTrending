package com.marshallaf.mobileui.injection

import android.app.Application
import com.marshallaf.mobileui.GithubTrendingApplication
import com.marshallaf.mobileui.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    ApplicationModule::class,
    CacheModule::class,
    DataModule::class,
    PresentationModule::class,
    RemoteModule::class,
    UiModule::class
))
interface ApplicationComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): ApplicationComponent
  }

  fun inject(app: GithubTrendingApplication)
}