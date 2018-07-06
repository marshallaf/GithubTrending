package com.marshallaf.domain.interactor.bookmark

import com.marshallaf.domain.executor.PostExecutionThread
import com.marshallaf.domain.interactor.CompletableUseCase
import com.marshallaf.domain.repository.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

class UnbookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {

  data class Params constructor(val projectId: String) {
    companion object {
      fun forProject(projectId: String): Params {
        return Params(projectId)
      }
    }
  }

  override fun buildUseCaseCompletable(params: Params?): Completable {
    if (params == null) throw IllegalArgumentException("params cannot be null")
    return projectsRepository.unbookmarkProject(params.projectId)
  }
}
