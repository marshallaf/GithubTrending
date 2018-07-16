package com.marshallaf.remote

import com.marshallaf.remote.mapper.ProjectsResponseModelMapper
import com.marshallaf.remote.service.GithubTrendingService
import com.marshallaf.remote.test.factory.ProjectDataFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import kotlin.test.Test

class ProjectsRemoteImplTest {

  private val service = mock<GithubTrendingService>()
  private val mapper = mock<ProjectsResponseModelMapper>()

  private val remoteImpl = ProjectsRemoteImpl(service, mapper)

  @Test fun getProjects_callsToService() {
    whenever(service.searchRepositories(any(), any(), any())).thenReturn(Observable.never())
    remoteImpl.getProjects().test()

    verify(service).searchRepositories("language:kotlin", "stars", "desc")
  }

  @Test fun getProjects_passesToMapper() {
    val response = ProjectDataFactory.makeProjectsResponse()
    whenever(service.searchRepositories(any(), any(), any())).thenReturn(Observable.just(response))
    remoteImpl.getProjects().test()

    verify(mapper, times(response.items.size)).mapFromModel(any())
  }

  @Test fun getProjects_completes() {
    val response = ProjectDataFactory.makeProjectsResponse()
    whenever(service.searchRepositories(any(), any(), any())).thenReturn(Observable.just(response))
    whenever(mapper.mapFromModel(any())).thenReturn(ProjectDataFactory.makeProjectEntity())
    remoteImpl.getProjects().test()
        .assertValueCount(1)
        .assertComplete()
  }
}
