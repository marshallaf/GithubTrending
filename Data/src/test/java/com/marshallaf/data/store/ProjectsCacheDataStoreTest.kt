package com.marshallaf.data.store

import com.marshallaf.data.repository.ProjectsCache
import com.marshallaf.data.test.factory.DataFactory
import com.marshallaf.data.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.CompletableSubject
import kotlin.test.Test

class ProjectsCacheDataStoreTest {

  private val projectsCache = mock<ProjectsCache>()

  private val projectsCacheDataStore = ProjectsCacheDataStore(projectsCache)

  @Test fun getProjects_returnsFromProjectsCache() {
    val list = listOf(ProjectFactory.makeProjectEntity())
    whenever(projectsCache.getProjects()).thenReturn(Observable.just(list))

    projectsCacheDataStore.getProjects().test()
        .assertValue(list)
        .assertComplete()
  }

  @Test fun saveProjects_savesToProjectsCache() {
    val list = listOf(ProjectFactory.makeProjectEntity())
    projectsCache.apply {
      whenever(this.saveProjects(any())).thenReturn(Completable.complete())
      whenever(this.setLastCacheTime(any())).thenReturn(Completable.complete())
    }

    projectsCacheDataStore.saveProjects(list)

    verify(projectsCache).saveProjects(list)
  }

  @Test fun saveProjects_setsLastCacheTime() {
    projectsCache.apply {
      whenever(this.saveProjects(any())).thenReturn(Completable.complete())
      whenever(this.setLastCacheTime(any())).thenReturn(Completable.complete())
    }

    projectsCacheDataStore.saveProjects(emptyList())

    verify(projectsCache).setLastCacheTime(any())
  }

  @Test fun saveProjects_returnsFromProjectsCache() {
    val completable = CompletableSubject.create()
    projectsCache.apply {
      whenever(this.saveProjects(any())).thenReturn(Completable.complete())
      whenever(this.setLastCacheTime(any())).thenReturn(completable)
    }

    projectsCacheDataStore.saveProjects(emptyList()).test().apply {
      assertNotComplete()
      completable.onComplete()
      assertComplete()
    }
  }

  @Test fun clearProjects_returnsFromProjectsCache() {
    val completable = CompletableSubject.create()
    whenever(projectsCache.clearProjects()).thenReturn(completable)

    projectsCacheDataStore.clearProjects().test().apply {
      assertNotComplete()
      completable.onComplete()
      assertComplete()
    }
  }

  @Test fun getBookmarkedProjects_returnsFromProjectsCache() {
    val list = listOf(ProjectFactory.makeProjectEntity())
    whenever(projectsCache.getBookmarkedProjects()).thenReturn(Observable.just(list))

    projectsCacheDataStore.getBookmarkedProjects().test()
        .assertValue(list)
        .assertComplete()
  }

  @Test fun setProjectBookmarked_setsOnProjectCache() {
    val projectId = DataFactory.randomString()
    projectsCacheDataStore.setProjectBookmarked(projectId)
    verify(projectsCache).setProjectBookmarked(projectId)
  }

  @Test fun setProjectBookmarked_returnsFromProjectsCache() {
    val completable = CompletableSubject.create()
    whenever(projectsCache.setProjectBookmarked(any())).thenReturn(completable)

    projectsCacheDataStore.setProjectBookmarked("").test().apply {
      assertNotComplete()
      completable.onComplete()
      assertComplete()
    }
  }

  @Test fun setProjectUnbookmarked_setsOnProjectCache() {
    val projectId = DataFactory.randomString()
    projectsCacheDataStore.setProjectUnbookmarked(projectId)
    verify(projectsCache).setProjectUnbookmarked(projectId)
  }

  @Test fun setProjectUnbookmarked_returnsFromProjectsCache() {
    val completable = CompletableSubject.create()
    whenever(projectsCache.setProjectUnbookmarked(any())).thenReturn(completable)

    projectsCacheDataStore.setProjectUnbookmarked("").test().apply {
      assertNotComplete()
      completable.onComplete()
      assertComplete()
    }
  }
}
