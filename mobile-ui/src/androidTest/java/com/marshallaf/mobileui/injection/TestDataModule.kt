package com.marshallaf.mobileui.injection

import com.marshallaf.domain.repository.ProjectsRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

  @Provides
  @JvmStatic
  @Singleton
  fun providesDataRepository() = mock<ProjectsRepository>()
}
