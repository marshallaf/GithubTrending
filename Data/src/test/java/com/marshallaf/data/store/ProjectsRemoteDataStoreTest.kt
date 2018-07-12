package com.marshallaf.data.store

import com.marshallaf.data.repository.ProjectsRemote
import com.marshallaf.data.test.factory.DataFactory
import com.marshallaf.data.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import kotlin.test.Test

class ProjectsRemoteDataStoreTest {

  private val projectsRemote = mock<ProjectsRemote>()

  private val projectsRemoteDataStore = ProjectsRemoteDataStore(projectsRemote)

  @Test fun getProjects_returnsFromProjectsRemote() {
    val list = listOf(ProjectFactory.makeProjectEntity())
    whenever(projectsRemote.getProjects()).thenReturn(Observable.just(list))
    projectsRemoteDataStore.getProjects().test()
        .assertValue(list)
        .assertComplete()
  }

  @Test(expected = UnsupportedOperationException::class)
  fun saveProjects_throwsException() {
    projectsRemoteDataStore.saveProjects(emptyList())
  }

  @Test(expected = UnsupportedOperationException::class)
  fun clearProjects_throwsException() {
    projectsRemoteDataStore.clearProjects()
  }

  @Test(expected = UnsupportedOperationException::class)
  fun getBookmarkedProjects_throwsException() {
    projectsRemoteDataStore.getBookmarkedProjects()
  }

  @Test(expected = UnsupportedOperationException::class)
  fun setProjectBookmarked_throwsException() {
    projectsRemoteDataStore.setProjectBookmarked(DataFactory.randomString())
  }

  @Test(expected = UnsupportedOperationException::class)
  fun setProjectUnbookmarked_throwsException() {
    projectsRemoteDataStore.setProjectUnbookmarked(DataFactory.randomString())
  }
}
