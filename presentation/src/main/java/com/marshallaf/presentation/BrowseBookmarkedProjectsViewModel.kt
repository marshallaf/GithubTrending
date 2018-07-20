package com.marshallaf.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.marshallaf.domain.interactor.bookmark.GetBookmarkedProjects
import com.marshallaf.domain.model.Project
import com.marshallaf.presentation.mapper.ProjectModelMapper
import com.marshallaf.presentation.model.ProjectModel
import com.marshallaf.presentation.state.Resource
import com.marshallaf.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookmarkedProjects,
    private val mapper: ProjectModelMapper
) : ViewModel() {

  private val liveData: MutableLiveData<Resource<List<ProjectModel>>> = MutableLiveData()

  override fun onCleared() {
    getBookmarkedProjects.dispose()
    super.onCleared()
  }

  fun getProjects() = liveData

  fun fetchProjects() {
    liveData.postValue(Resource(ResourceState.LOADING, null, null))
    return getBookmarkedProjects.execute(ProjectsSubscriber())
  }

  inner class ProjectsSubscriber : DisposableObserver<List<Project>>() {
    override fun onComplete() { }

    override fun onNext(projects: List<Project>) {
      liveData.postValue(Resource(ResourceState.SUCCESS, projects.map { mapper.mapToModel(it) }, null))
    }

    override fun onError(e: Throwable) {
      liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
    }
  }
}