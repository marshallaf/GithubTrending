package com.marshallaf.domain.interactor.browse

import com.marshallaf.domain.executor.PostExecutionThread
import com.marshallaf.domain.interactor.ObservableUseCase
import com.marshallaf.domain.model.Project
import com.marshallaf.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Project>, Nothing>(postExecutionThread) {

  override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
    return projectsRepository.getProjects()
  }
}
