package com.marshallaf.domain.interactor.bookmark

import com.marshallaf.domain.executor.PostExecutionThread
import com.marshallaf.domain.repository.ProjectsRepository
import com.marshallaf.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class UnbookmarkProjectTest {

  lateinit var unbookmarkProject: UnbookmarkProject

  @Mock lateinit var projectsRepository: ProjectsRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before fun setUp() {
    MockitoAnnotations.initMocks(this)

    unbookmarkProject = UnbookmarkProject(projectsRepository, postExecutionThread)
  }

  @Test fun unbookmarkProject_completes() {
    val projectId = ProjectDataFactory.randomUuid()
    whenever(projectsRepository.unbookmarkProject(any())).thenReturn(Completable.complete())
    unbookmarkProject.buildUseCaseCompletable(UnbookmarkProject.Params.forProject(projectId))
        .test()
        .assertComplete()
    verify(projectsRepository).unbookmarkProject(projectId)
  }

  @Test(expected = IllegalArgumentException::class)
  fun unbookmarkProject_nullParams_throwsException() {
    unbookmarkProject.buildUseCaseCompletable(null).test()
  }
}
