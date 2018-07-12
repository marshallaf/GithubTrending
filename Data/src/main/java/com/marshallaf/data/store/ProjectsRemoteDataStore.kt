package com.marshallaf.data.store

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.data.repository.ProjectsDataStore
import com.marshallaf.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteDataStore @Inject constructor(
    private val projectsRemote: ProjectsRemote
): ProjectsDataStore {

  override fun getProjects(): Observable<List<ProjectEntity>> {
    return projectsRemote.getProjects()
  }

  override fun saveProjects(projects: List<ProjectEntity>): Completable {
    throw UnsupportedOperationException()
  }

  override fun clearProjects(): Completable {
    throw UnsupportedOperationException()
  }

  override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
    throw UnsupportedOperationException()
  }

  override fun setProjectBookmarked(projectId: String): Completable {
    throw UnsupportedOperationException()
  }

  override fun setProjectUnbookmarked(projectId: String): Completable {
    throw UnsupportedOperationException()
  }
}
