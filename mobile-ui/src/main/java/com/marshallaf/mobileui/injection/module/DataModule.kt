package com.marshallaf.mobileui.injection.module

import com.marshallaf.data.ProjectsDataRepository
import com.marshallaf.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

  @Binds abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository
}
