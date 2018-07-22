package com.marshallaf.mobileui.injection.module

import com.marshallaf.domain.executor.PostExecutionThread
import com.marshallaf.mobileui.BrowseActivity
import com.marshallaf.mobileui.UiThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

  @Binds abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

  @ContributesAndroidInjector abstract fun contributesBrowseActivity(): BrowseActivity
}