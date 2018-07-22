package com.marshallaf.mobileui.injection

import android.app.Application
import com.marshallaf.cache.db.ProjectsDatabase
import com.marshallaf.data.repository.ProjectsCache
import com.marshallaf.data.repository.ProjectsRemote
import com.marshallaf.mobileui.BuildConfig
import com.marshallaf.remote.service.GithubTrendingService
import com.marshallaf.remote.service.GithubTrendingServiceFactory
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {

  @Provides
  @JvmStatic
  fun providesGithubService() = mock<GithubTrendingService>()

  @Provides
  @JvmStatic
  fun providesProjectsRemote() = mock<ProjectsRemote>()
}
