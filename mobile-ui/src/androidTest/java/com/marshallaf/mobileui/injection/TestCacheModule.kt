package com.marshallaf.mobileui.injection

import android.app.Application
import com.marshallaf.cache.db.ProjectsDatabase
import com.marshallaf.data.repository.ProjectsCache
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

  @Provides
  @JvmStatic
  fun providesDatabase(application: Application) = ProjectsDatabase.getInstance(application)

  @Provides
  @JvmStatic
  fun providesProjectsCache() = mock<ProjectsCache>()
}
