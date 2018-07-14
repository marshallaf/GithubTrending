package com.marshallaf.data.store

import com.marshallaf.data.repository.ProjectsDataStore
import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
    private val projectsCacheDataStore: ProjectsCacheDataStore,
    private val projectsRemoteDataStore: ProjectsRemoteDataStore
) {

  open fun getDataStore(projectsCached: Boolean, cacheExpired: Boolean): ProjectsDataStore {
    return if (projectsCached && !cacheExpired) {
      projectsCacheDataStore
    } else {
      projectsRemoteDataStore
    }
  }

  open fun getCacheDataStore(): ProjectsDataStore {
    return projectsCacheDataStore
  }
}
