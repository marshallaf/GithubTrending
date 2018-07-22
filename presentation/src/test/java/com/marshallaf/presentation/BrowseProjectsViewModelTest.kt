package com.marshallaf.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.marshallaf.domain.interactor.bookmark.BookmarkProject
import com.marshallaf.domain.interactor.bookmark.UnbookmarkProject
import com.marshallaf.domain.interactor.browse.GetProjects
import com.marshallaf.domain.model.Project
import com.marshallaf.presentation.mapper.ProjectModelMapper
import com.marshallaf.presentation.state.ResourceState
import com.marshallaf.presentation.test.factory.DataFactory
import com.marshallaf.presentation.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BrowseProjectsViewModelTest {

  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var getProjects: GetProjects
  private lateinit var bookmarkProject: BookmarkProject
  private lateinit var unbookmarkProject: UnbookmarkProject
  private lateinit var projectMapper: ProjectModelMapper

  private lateinit var browseProjectsViewModel: BrowseProjectsViewModel

  private val captor = argumentCaptor<DisposableObserver<List<Project>>>()

  @BeforeTest fun setUp() {
    getProjects = mock()
    bookmarkProject = mock()
    unbookmarkProject = mock()
    projectMapper = mock()

    browseProjectsViewModel = BrowseProjectsViewModel(getProjects, bookmarkProject, unbookmarkProject, projectMapper)
  }

  @Test fun fetchProjects_executesUseCase() {
    browseProjectsViewModel.fetchProjects()

    verify(getProjects, times(1)).execute(any(), eq(null))
  }

  @Test fun fetchProjects_onNext_returnsSuccess() {
    val projects = List(2) { ProjectFactory.makeProject() }
    val projectModels = List(2) { ProjectFactory.makeProjectModel() }
    projects.indices.forEach {
      whenever(projectMapper.mapToModel(projects[it])).thenReturn(projectModels[it])
    }

    browseProjectsViewModel.fetchProjects()

    verify(getProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onNext(projects)
    assertEquals(ResourceState.SUCCESS, browseProjectsViewModel.getProjects().value?.status)
  }

  @Test fun fetchProjects_onNext_returnsExpectedData() {
    val projects = List(2) { ProjectFactory.makeProject() }
    val projectModels = List(2) { ProjectFactory.makeProjectModel() }
    projects.indices.forEach {
      whenever(projectMapper.mapToModel(projects[it])).thenReturn(projectModels[it])
    }

    browseProjectsViewModel.fetchProjects()

    verify(getProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onNext(projects)
    assertEquals(projectModels, browseProjectsViewModel.getProjects().value?.data)
  }

  @Test fun fetchProjects_onError_returnsError() {
    browseProjectsViewModel.fetchProjects()

    verify(getProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onError(RuntimeException())
    assertEquals(ResourceState.ERROR, browseProjectsViewModel.getProjects().value?.status)
  }

  @Test fun fetchProjects_onError_returnsErrorMessage() {
    val errorMessage = DataFactory.randomString()
    browseProjectsViewModel.fetchProjects()

    verify(getProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onError(RuntimeException(errorMessage))
    assertEquals(errorMessage, browseProjectsViewModel.getProjects().value?.message)
  }
}
