package com.marshallaf.domain.interactor.browse

import com.marshallaf.domain.executor.PostExecutionThread
import com.marshallaf.domain.model.Project
import com.marshallaf.domain.repository.ProjectsRepository
import com.marshallaf.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest {

  private lateinit var getProjects: GetProjects

  @Mock lateinit var projectsRepository: ProjectsRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before fun setup() {
    MockitoAnnotations.initMocks(this)
    getProjects = GetProjects(projectsRepository, postExecutionThread)
  }

  @Test fun getProjects_completes() {
    whenever(projectsRepository.getProjects()).thenReturn(Observable.just(ProjectDataFactory.makeProjectList(2)))
    getProjects.buildUseCaseObservable().test()
        .assertComplete()
  }

  @Test fun getProjects_returnsFromRepository() {
    val projectList = ProjectDataFactory.makeProjectList(2)
    whenever(projectsRepository.getProjects()).thenReturn(Observable.just(projectList))
    getProjects.buildUseCaseObservable().test()
        .assertValue(projectList)
  }
}
