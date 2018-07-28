package com.marshallaf.mobileui.injection

import android.app.Application
import com.marshallaf.domain.repository.ProjectsRepository
import com.marshallaf.mobileui.browse.BrowseActivityTest
import com.marshallaf.mobileui.injection.module.PresentationModule
import com.marshallaf.mobileui.injection.module.UiModule
import com.marshallaf.mobileui.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    TestApplicationModule::class,
    TestCacheModule::class,
    TestDataModule::class,
    PresentationModule::class,
    TestRemoteModule::class,
    UiModule::class
))
interface TestApplicationComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): TestApplicationComponent.Builder

    fun build(): TestApplicationComponent
  }

  fun inject(application: TestApplication)
  fun projectsRepository(): ProjectsRepository
}
