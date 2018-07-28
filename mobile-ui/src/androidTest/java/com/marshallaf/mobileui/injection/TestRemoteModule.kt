package com.marshallaf.mobileui.injection

import com.marshallaf.data.repository.ProjectsRemote
import com.marshallaf.remote.service.GithubTrendingService
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
