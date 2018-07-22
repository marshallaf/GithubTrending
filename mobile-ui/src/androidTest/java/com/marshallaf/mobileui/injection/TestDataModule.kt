package com.marshallaf.mobileui.injection

import com.marshallaf.data.ProjectsDataRepository
import com.marshallaf.domain.repository.ProjectsRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object TestDataModule {

  @Provides
  @JvmStatic
  fun providesDataRepository() = mock<ProjectsRepository>()
}
