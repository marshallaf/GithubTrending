package com.marshallaf.mobileui.injection.module

import android.app.Application
import com.marshallaf.cache.ProjectsCacheImpl
import com.marshallaf.cache.db.ProjectsDatabase
import com.marshallaf.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

  @Module
  companion object {

    @Provides
    @JvmStatic
    fun providesDatabase(application: Application) = ProjectsDatabase.getInstance(application)
  }

  @Binds abstract fun bindProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache
}
