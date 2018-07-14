package com.marshallaf.data.store

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.data.repository.ProjectsCache
import com.marshallaf.data.repository.ProjectsDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsCacheDataStore @Inject constructor(
    private val projectsCache: ProjectsCache
): ProjectsDataStore {

  override fun getProjects(): Observable<List<ProjectEntity>> {
    return projectsCache.getProjects()
  }

  override fun saveProjects(projects: List<ProjectEntity>): Completable {
    return projectsCache.saveProjects(projects)
        .andThen(projectsCache.setLastCacheTime(System.currentTimeMillis()))
  }

  override fun clearProjects(): Completable {
    return projectsCache.clearProjects()
  }

  override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
    return projectsCache.getBookmarkedProjects()
  }

  override fun setProjectBookmarked(projectId: String): Completable {
    return projectsCache.setProjectBookmarked(projectId)
  }

  override fun setProjectUnbookmarked(projectId: String): Completable {
    return projectsCache.setProjectUnbookmarked(projectId)
  }
}
