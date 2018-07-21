package com.marshallaf.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.marshallaf.domain.interactor.bookmark.GetBookmarkedProjects
import com.marshallaf.domain.model.Project
import com.marshallaf.presentation.mapper.ProjectModelMapper
import com.marshallaf.presentation.state.ResourceState
import com.marshallaf.presentation.test.factory.DataFactory
import com.marshallaf.presentation.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.Test

@RunWith(MockitoJUnitRunner::class)
class BrowseBookmarkedProjectsViewModelTest {

  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  @Mock lateinit var getBookmarkedProjects: GetBookmarkedProjects
  @Mock lateinit var mapper: ProjectModelMapper

  @InjectMocks lateinit var viewModel: BrowseBookmarkedProjectsViewModel

  private val captor = argumentCaptor<DisposableObserver<List<Project>>>()

  @Test fun fetchProjects_callsToUseCase() {
    viewModel.fetchProjects()

    verify(getBookmarkedProjects, times(1)).execute(any(), eq(null))
  }

  @Test fun fetchProjects_onNext_returnsSuccess() {
    val projects = List(2) { ProjectFactory.makeProject() }
    val projectModels = List(2) { ProjectFactory.makeProjectModel() }
    projects.indices.forEach {
      whenever(mapper.mapToModel(projects[it])).thenReturn(projectModels[it])
    }
    viewModel.fetchProjects()

    verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onNext(projects)
    assertEquals(ResourceState.SUCCESS, viewModel.getProjects().value?.status)
  }

  @Test fun fetchProjects_onNext_returnsExpectedData() {
    val projects = List(2) { ProjectFactory.makeProject() }
    val projectModels = List(2) { ProjectFactory.makeProjectModel() }
    projects.indices.forEach {
      whenever(mapper.mapToModel(projects[it])).thenReturn(projectModels[it])
    }
    viewModel.fetchProjects()

    verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onNext(projects)
    assertEquals(projectModels, viewModel.getProjects().value?.data)
  }

  @Test fun fetchProjects_onError_returnsError() {
    viewModel.fetchProjects()

    verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onError(RuntimeException())
    assertEquals(ResourceState.ERROR, viewModel.getProjects().value?.status)
  }

  @Test fun fetchProjects_onError_returnsErrorMessage() {
    val errorMessage = DataFactory.randomString()
    viewModel.fetchProjects()

    verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
    captor.firstValue.onError(RuntimeException(errorMessage))
    assertEquals(errorMessage, viewModel.getProjects().value?.message)
  }
}
