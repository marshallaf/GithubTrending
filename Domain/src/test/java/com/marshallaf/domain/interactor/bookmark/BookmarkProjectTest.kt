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

class BookmarkProjectTest {

  lateinit var bookmarkProject: BookmarkProject

  @Mock lateinit var projectsRepository: ProjectsRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before fun setUp() {
    MockitoAnnotations.initMocks(this)

    bookmarkProject = BookmarkProject(projectsRepository, postExecutionThread)
  }

  @Test fun bookmarkProject_completes() {
    val projectId = ProjectDataFactory.randomUuid()
    whenever(projectsRepository.bookmarkProject(any())).thenReturn(Completable.complete())
    bookmarkProject.buildUseCaseCompletable(BookmarkProject.Params.forProject(projectId))
        .test()
        .assertComplete()
    verify(projectsRepository).bookmarkProject(projectId)
  }

  @Test(expected = IllegalArgumentException::class)
  fun bookmarkProject_nullParams_throwsException() {
    bookmarkProject.buildUseCaseCompletable(null).test()
  }
}
