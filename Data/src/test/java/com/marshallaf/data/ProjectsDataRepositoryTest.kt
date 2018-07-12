package com.marshallaf.data

import com.marshallaf.data.mapper.ProjectMapper
import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.data.repository.ProjectsCache
import com.marshallaf.data.repository.ProjectsDataStore
import com.marshallaf.data.store.ProjectsDataStoreFactory
import com.marshallaf.data.test.factory.DataFactory
import com.marshallaf.data.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

private const val LIST_SIZE = 3

class ProjectsDataRepositoryTest {

  val projectEntityList = makeProjectEntityList(LIST_SIZE)

  val mapper = mock<ProjectMapper>()
  val cache = mock<ProjectsCache>()
  val factory = mock<ProjectsDataStoreFactory>()
  val dataStore = mock<ProjectsDataStore>()

  val repository = ProjectsDataRepository(mapper, cache, factory)

  @Before fun setUp() {
    whenever(factory.getCacheDataStore()).thenReturn(dataStore)
    whenever(factory.getDataStore(any(), any())).thenReturn(dataStore)
    whenever(cache.areProjectsCached()).thenReturn(Single.just(true))
    whenever(cache.isProjectsCacheExpired()).thenReturn(Single.just(false))
    whenever(dataStore.getProjects()).thenReturn(Observable.just(projectEntityList))
    whenever(dataStore.getBookmarkedProjects()).thenReturn(Observable.just(projectEntityList))
    whenever(dataStore.setProjectBookmarked(any())).thenReturn(Completable.complete())
    whenever(dataStore.setProjectUnbookmarked(any())).thenReturn(Completable.complete())
    whenever(mapper.mapFromEntity(any())).thenReturn(ProjectFactory.makeProject())
  }

  @Test fun getProjects_nonZeroProjects_returnsAsExpected() {
    repository.getProjects().test()
        .assertValue {
          it.size == LIST_SIZE
        }
        .assertComplete()
    verify(factory).getDataStore(any(), any())
    verify(dataStore).getProjects()
    verify(dataStore).saveProjects(projectEntityList)
  }

  @Test fun getProjects_zeroProjects_returnsEmptyList() {
    whenever(dataStore.getProjects()).thenReturn(Observable.just(emptyList()))
    repository.getProjects().test()
        .assertValue {
          it.isEmpty()
        }
        .assertComplete()
    verify(factory).getDataStore(any(), any())
    verify(dataStore).getProjects()
    verify(dataStore).saveProjects(emptyList())
  }

  @Test fun bookmarkProject_bookmarksOnCacheDataStore() {
    val projectId = DataFactory.randomString()
    repository.bookmarkProject(projectId).test()
        .assertComplete()
    verify(dataStore).setProjectBookmarked(projectId)
  }

  @Test fun unbookmarkProject_unbookmarksOnCacheDataStore() {
    val projectId = DataFactory.randomString()
    repository.unbookmarkProject(projectId).test()
        .assertComplete()
    verify(dataStore).setProjectUnbookmarked(projectId)
  }

  @Test fun getBookmarkedProjects_nonZeroBookmarks_returnsAsExpected() {
    repository.getBookmarkedProjects().test()
        .assertValue {
          it.size == LIST_SIZE
        }
        .assertComplete()
    verify(dataStore).getBookmarkedProjects()
  }

  @Test fun getBookmarkedProjects_zeroBookmarks_returnsEmptyList() {
    whenever(dataStore.getBookmarkedProjects()).thenReturn(Observable.just(emptyList()))
    repository.getBookmarkedProjects().test()
        .assertValue {
          it.isEmpty()
        }
        .assertComplete()
    verify(dataStore).getBookmarkedProjects()
  }

  private fun makeProjectEntityList(size: Int): List<ProjectEntity> {
    val projectEntityList = mutableListOf<ProjectEntity>()
    repeat(size) {
      projectEntityList.add(ProjectFactory.makeProjectEntity())
    }
    return projectEntityList
  }
}
