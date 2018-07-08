package com.marshallaf.domain.interactor.bookmark

import com.marshallaf.domain.executor.PostExecutionThread
import com.marshallaf.domain.repository.ProjectsRepository
import com.marshallaf.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetBookmarkedProjectsTest {

  private lateinit var getBookmarkedProjects: GetBookmarkedProjects

  @Mock lateinit var projectsRepository: ProjectsRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before fun setUp() {
    MockitoAnnotations.initMocks(this)

    getBookmarkedProjects = GetBookmarkedProjects(projectsRepository, postExecutionThread)
  }

  @Test fun getBookmarkedProjects_completes() {
    whenever(projectsRepository.getBookmarkedProjects()).thenReturn(Observable.just(ProjectDataFactory.makeProjectList(2)))
    getBookmarkedProjects.buildUseCaseObservable().test()
        .assertComplete()
  }

  @Test fun getBookmarkedProjects_returnsFromRepository() {
    val projectList = ProjectDataFactory.makeProjectList(2)
    whenever(projectsRepository.getBookmarkedProjects()).thenReturn(Observable.just(projectList))
    getBookmarkedProjects.buildUseCaseObservable().test()
        .assertValue(projectList)
  }
}
