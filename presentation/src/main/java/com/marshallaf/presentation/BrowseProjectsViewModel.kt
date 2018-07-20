package com.marshallaf.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.marshallaf.domain.interactor.bookmark.BookmarkProject
import com.marshallaf.domain.interactor.bookmark.UnbookmarkProject
import com.marshallaf.domain.interactor.browse.GetProjects
import com.marshallaf.domain.model.Project
import com.marshallaf.presentation.mapper.ProjectModelMapper
import com.marshallaf.presentation.model.ProjectModel
import com.marshallaf.presentation.state.Resource
import com.marshallaf.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
    private val getProjects: GetProjects,
    private val bookmarkProject: BookmarkProject,
    private val unbookmarkProject: UnbookmarkProject,
    private val mapper: ProjectModelMapper
) : ViewModel() {

  private val liveData: MutableLiveData<Resource<List<ProjectModel>>> = MutableLiveData()

  override fun onCleared() {
    getProjects.dispose()
    super.onCleared()
  }

  fun getProjects(): LiveData<Resource<List<ProjectModel>>> {
    return liveData
  }

  fun fetchProjects() {
    liveData.postValue(Resource(ResourceState.LOADING, null, null))
    return getProjects.execute(ProjectsSubscriber())
  }

  fun bookmarkProject(projectId: String) {
    liveData.postValue(Resource(ResourceState.LOADING, null, null))
    bookmarkProject.execute(BookmarkProjectSubscriber(), BookmarkProject.Params.forProject(projectId))
  }

  fun unbookmarkProject(projectId: String) {
    liveData.postValue(Resource(ResourceState.LOADING, null, null))
    unbookmarkProject.execute(BookmarkProjectSubscriber(), UnbookmarkProject.Params.forProject(projectId))
  }

  inner class ProjectsSubscriber: DisposableObserver<List<Project>>() {
    override fun onNext(projects: List<Project>) {
      liveData.postValue(Resource(ResourceState.SUCCESS, projects.map { mapper.mapToModel(it) }, null))
    }

    override fun onError(e: Throwable) {
      liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
    }

    override fun onComplete() { }
  }

  inner class BookmarkProjectSubscriber: DisposableCompletableObserver() {
    override fun onError(e: Throwable) {
      liveData.postValue(Resource(ResourceState.ERROR, liveData.value?.data, e.localizedMessage))
    }

    override fun onComplete() {
      liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data, null))
    }
  }
}