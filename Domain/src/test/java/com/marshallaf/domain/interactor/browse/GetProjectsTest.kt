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
    stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
    val testObserver = getProjects.buildUseCaseObservable().test()
    testObserver.assertComplete()
  }

  @Test fun getProjects_returnsExpectedData() {
    val projects = ProjectDataFactory.makeProjectList(2)
    stubGetProjects(Observable.just(projects))
    val testObserver = getProjects.buildUseCaseObservable().test()
    testObserver.assertValue(projects)
  }

  private fun stubGetProjects(observable: Observable<List<Project>>) {
    whenever(projectsRepository.getProjects())
        .thenReturn(observable)
  }
}