package com.marshallaf.remote

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.data.repository.ProjectsRemote
import com.marshallaf.remote.mapper.ProjectsResponseModelMapper
import com.marshallaf.remote.service.GithubTrendingService
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
    private val service: GithubTrendingService,
    private val mapper: ProjectsResponseModelMapper
) : ProjectsRemote {

  override fun getProjects(): Observable<List<ProjectEntity>> {
    return service.searchRepositories("language:kotlin", "stars", "desc")
        .map {
          it.items.map { mapper.mapFromModel(it) }
        }
  }

}
