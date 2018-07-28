package com.marshallaf.mobileui.injection.module

import com.marshallaf.data.repository.ProjectsRemote
import com.marshallaf.mobileui.BuildConfig
import com.marshallaf.remote.ProjectsRemoteImpl
import com.marshallaf.remote.service.GithubTrendingService
import com.marshallaf.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

  @Module
  companion object {

    @Provides
    @JvmStatic
    fun provideGithubService() = GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
  }

  @Binds abstract fun bindsProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote
}
